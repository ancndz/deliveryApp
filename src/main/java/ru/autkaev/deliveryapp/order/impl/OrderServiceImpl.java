package ru.autkaev.deliveryapp.order.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.autkaev.deliveryapp.order.OrderService;
import ru.autkaev.deliveryapp.order.domain.ClientOrder;
import ru.autkaev.deliveryapp.order.domain.repo.OrderRepository;

import java.util.List;
import java.util.Optional;

/**
 * Реализация работы с заказами.
 *
 * @author Anton Utkaev
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderServiceImpl(final OrderRepository orderRepository,
            final ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<ClientOrder> findById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<ClientOrder> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void save(final ClientOrder clientOrder) {
        if (clientOrder.isNew()) {
            applicationEventPublisher.publishEvent(new OrderCreatingEvent(this, clientOrder));
        }
        orderRepository.save(clientOrder);
    }

}
