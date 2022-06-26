package ru.autkaev.deliveryapp.courier.controller;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import ru.autkaev.deliveryapp.courier.CourierService;
import ru.autkaev.deliveryapp.courier.domain.Courier;
import ru.autkaev.deliveryapp.delivery.controller.DeliveryController;
import ru.autkaev.deliveryapp.order.controller.OrderController;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Контроллер получения курьеров.
 *
 * @author Anton Utkaev
 */
@Route("courier")
public class CourierController extends AppLayout {

    private final Grid<Courier> grid;

    private final CourierService courierService;

    public CourierController(final CourierService courierService) {
        this.courierService = courierService;

        final VerticalLayout layout = new VerticalLayout();
        this.grid = new Grid<>(Courier.class);
        final RouterLink linkCreate = new RouterLink("Добавить курьера", ManageCourierController.class, "new");
        final RouterLink linkDeliveries = new RouterLink("Заказы", DeliveryController.class);
        final RouterLink linkOrders = new RouterLink("Доставки", OrderController.class);
        layout.add(linkCreate);
        layout.add(linkDeliveries);
        layout.add(linkOrders);
        layout.add(grid);
        addToNavbar(new H3("Список курьеров"));
        setContent(layout);
    }

    @PostConstruct
    public void fillGrid() {
        final List<Courier> couriers = courierService.findAll();
        if (!couriers.isEmpty()) {
            grid.addColumn(new NativeButtonRenderer<>("Редактировать",
                    contact -> UI.getCurrent().navigate(ManageCourierController.class, contact.getId())));
            grid.addColumn(new NativeButtonRenderer<>("Удалить", contact -> {
                final Dialog dialog = new Dialog();
                final Button confirm = new Button("Удалить");
                final Button cancel = new Button("Отмена");
                dialog.add("Вы уверены что хотите удалить курьера?");
                dialog.add(confirm);
                dialog.add(cancel);
                confirm.addClickListener(clickEvent -> {
                    courierService.deleteById(contact.getId());
                    dialog.close();
                    Notification notification = new Notification("Курьер удален", 1000);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();
                    grid.setItems(courierService.findAll());
                });
                cancel.addClickListener(clickEvent -> dialog.close());
                dialog.open();
            }));
            grid.setItems(couriers);
        }
    }

}
