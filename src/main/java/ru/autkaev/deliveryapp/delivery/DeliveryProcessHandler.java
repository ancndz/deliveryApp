package ru.autkaev.deliveryapp.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.autkaev.deliveryapp.delivery.domain.Delivery;
import ru.autkaev.deliveryapp.order.OrderService;
import ru.autkaev.deliveryapp.order.domain.ClientOrder;
import ru.autkaev.deliveryapp.order.domain.OrderSizeEnum;
import ru.autkaev.deliveryapp.order.domain.repo.OrderRepository;
import ru.autkaev.deliveryapp.order.impl.OrderCreatingEvent;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Обработчик создания доставок.
 *
 * @author Anton Utkaev
 */
@Component
public class DeliveryProcessHandler {

    private final static Logger LOG = LoggerFactory.getLogger(DeliveryProcessHandler.class);

    private final DeliveryService deliveryService;

    private final OrderService orderService;

    public DeliveryProcessHandler(final DeliveryService deliveryService, final OrderService orderService) {
        this.deliveryService = deliveryService;
        this.orderService = orderService;
    }

    @TransactionalEventListener(value = OrderCreatingEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void onAfterCreate(final OrderCreatingEvent event) {
        LOG.info("Start of " + DeliveryProcessHandler.class.getSimpleName());
        final ClientOrder clientOrder = event.getClientOrder();

        if (clientOrder.getDelivery() != null) {
            LOG.info("No need to process.");
            return;
        }

        final List<Delivery> acceptableDeliveries = deliveryService.findAll()
                .stream()
                .filter(delivery -> delivery.getAddress().equals(clientOrder.getAddress()))
                .filter(delivery -> delivery.getClientOrders().size() < 10)
                .filter(delivery -> !delivery.isCompleted())
                .collect(Collectors.toList());

        if (acceptableDeliveries.isEmpty()) {
            createNewDelivery(clientOrder);
            return;
        }

        if (OrderSizeEnum.LARGE.equals(clientOrder.getOrderSizeEnum())) {
            final Optional<Delivery> acceptableDelivery = acceptableDeliveries.stream()
                    .filter(delivery -> delivery.getMaxOrderSize().equals(OrderSizeEnum.LARGE))
                    .sorted(Comparator.comparing(delivery -> delivery.getClientOrders().size()))
                    .findAny();
            acceptableDelivery.ifPresent(delivery -> moveClientOrderToDelivery(clientOrder, delivery));
        } else if (OrderSizeEnum.MEDIUM.equals(clientOrder.getOrderSizeEnum())) {
            final Optional<Delivery> acceptableDelivery = acceptableDeliveries.stream()
                    .filter(delivery -> delivery.getMaxOrderSize().equals(OrderSizeEnum.LARGE)
                            || delivery.getMaxOrderSize().equals(OrderSizeEnum.MEDIUM))
                    .sorted(Comparator.comparing(delivery -> delivery.getClientOrders().size()))
                    .findAny();
            acceptableDelivery.ifPresent(delivery -> moveClientOrderToDelivery(clientOrder, delivery));
        } else {
            final Optional<Delivery> acceptableDelivery = acceptableDeliveries.stream()
                    .sorted(Comparator.comparing(delivery -> delivery.getClientOrders().size()))
                    .findAny();
            acceptableDelivery.ifPresent(delivery -> moveClientOrderToDelivery(clientOrder, delivery));
        }

    }

    private void moveClientOrderToDelivery(final ClientOrder clientOrder, final Delivery delivery) {
        LOG.info("Updating existing delivery");
        clientOrder.setDelivery(delivery);
        delivery.getClientOrders().add(clientOrder);

        deliveryService.save(delivery);
        orderService.save(clientOrder);
    }

    private void createNewDelivery(final ClientOrder clientOrder) {
        LOG.info("Creating new delivery");
        final Delivery delivery = new Delivery();

        delivery.setId(UUID.randomUUID().toString());
        delivery.setAddress(clientOrder.getAddress());
        delivery.setCompleted(false);

        delivery.setMaxOrderFragility(clientOrder.getOrderFragilityEnum());
        delivery.setMaxOrderSize(clientOrder.getOrderSizeEnum());
        delivery.setMaxOrderValue(clientOrder.getOrderValueEnum());
        delivery.setMaxOrderWeight(clientOrder.getOrderWeightEnum());
        delivery.getClientOrders().add(clientOrder);

        clientOrder.setDelivery(delivery);

        deliveryService.save(delivery);
        orderService.save(clientOrder);
    }
}
