package ru.autkaev.deliveryapp.delivery.controller;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.hibernate.Hibernate;
import ru.autkaev.deliveryapp.courier.controller.CourierController;
import ru.autkaev.deliveryapp.delivery.DeliveryService;
import ru.autkaev.deliveryapp.delivery.domain.Delivery;
import ru.autkaev.deliveryapp.order.controller.ManageOrderController;
import ru.autkaev.deliveryapp.order.controller.OrderController;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Контроллер для работы с доставками.
 *
 * @author Anton Utkaev
 */
@Route("delivery")
public class DeliveryController extends AppLayout {

    private final Grid<Delivery> grid;

    private final DeliveryService deliveryService;

    public DeliveryController(final DeliveryService deliveryService) {
        this.deliveryService = deliveryService;

        final VerticalLayout layout = new VerticalLayout();
        this.grid = new Grid<>(Delivery.class);
        final RouterLink linkCreate = new RouterLink("Создать заказ", ManageOrderController.class);
        final RouterLink linkCouriers = new RouterLink("Курьеры", CourierController.class);
        final RouterLink linkOrders = new RouterLink("Заказы", OrderController.class);
        layout.add(linkCreate);
        layout.add(linkCouriers);
        layout.add(linkOrders);
        layout.add(grid);
        addToNavbar(new H3("Список доставок"));
        setContent(layout);
    }

    @PostConstruct
    public void fillGrid() {
        final List<Delivery> deliveryList = deliveryService.findAll();
        if (!deliveryList.isEmpty()) {
            grid.setItems(deliveryList);
        }
    }
}
