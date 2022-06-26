package ru.autkaev.deliveryapp.order.controller;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import ru.autkaev.deliveryapp.courier.controller.CourierController;
import ru.autkaev.deliveryapp.delivery.controller.DeliveryController;
import ru.autkaev.deliveryapp.order.OrderService;
import ru.autkaev.deliveryapp.order.domain.ClientOrder;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Контроллер для работы с заказами.
 *
 * @author Anton Utkaev
 */
@Route("order")
public class OrderController extends AppLayout {

    private final Grid<ClientOrder> grid;

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;

        VerticalLayout layout = new VerticalLayout();
        this.grid = new Grid<>(ClientOrder.class);
        final RouterLink linkCreate = new RouterLink("Создать заказ", ManageOrderController.class);
        final RouterLink linkCouriers = new RouterLink("Курьеры", CourierController.class);
        final RouterLink linkDeliveries = new RouterLink("Доставки", DeliveryController.class);
        layout.add(linkCreate);
        layout.add(linkCouriers);
        layout.add(linkDeliveries);
        layout.add(grid);
        addToNavbar(new H2("Список заказов"));
        setContent(layout);
    }

    @PostConstruct
    public void fillGrid() {
        final List<ClientOrder> clientOrders = orderService.findAll();
        if (!clientOrders.isEmpty()) {
            grid.addColumn(new NativeButtonRenderer<>("Удалить", order -> {
                final Dialog dialog = new Dialog();
                final Button confirm = new Button("Удалить");
                final Button cancel = new Button("Отмена");
                dialog.add("Вы уверены что хотите удалить заказ?");
                dialog.add(confirm);
                dialog.add(cancel);
                confirm.addClickListener(clickEvent -> {
                    orderService.deleteById(order.getId());
                    dialog.close();
                    Notification notification = new Notification("Заказ удален", 1000);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();
                    grid.setItems(orderService.findAll());
                });
                cancel.addClickListener(clickEvent -> dialog.close());
                dialog.open();
            }));
            grid.setItems(clientOrders);
        }
    }

}
