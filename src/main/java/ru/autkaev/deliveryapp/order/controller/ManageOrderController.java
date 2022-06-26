package ru.autkaev.deliveryapp.order.controller;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.autkaev.deliveryapp.order.OrderService;
import ru.autkaev.deliveryapp.order.domain.ClientOrder;
import ru.autkaev.deliveryapp.order.domain.OrderFragilityEnum;
import ru.autkaev.deliveryapp.order.domain.OrderSizeEnum;
import ru.autkaev.deliveryapp.order.domain.OrderValueEnum;
import ru.autkaev.deliveryapp.order.domain.OrderWeightEnum;

import java.util.EnumSet;
import java.util.UUID;

/**
 * Контроллер для работы с заказами.
 *
 * @author Anton Utkaev
 */
@Route("manage-order")
public class ManageOrderController extends AppLayout {

    private final OrderService orderService;

    private final FormLayout orderForm;

    private final ComboBox<OrderSizeEnum> orderSizeEnumComboBox;

    private final ComboBox<OrderWeightEnum> orderWeightEnumComboBox;

    private final ComboBox<OrderFragilityEnum> orderFragilityEnumComboBox;

    private final ComboBox<OrderValueEnum> orderValueEnumComboBox;

    private final TextField addressField;

    private final DatePicker startDatePicker;

    private final DatePicker endDatePicker;

    private final Button saveOrder;

    public ManageOrderController(final OrderService orderService) {
        this.orderService = orderService;

        orderForm = new FormLayout();

        orderSizeEnumComboBox = new ComboBox<>("Габариты посылки");
        orderSizeEnumComboBox.setItems(EnumSet.allOf(OrderSizeEnum.class));

        orderWeightEnumComboBox = new ComboBox<>("Вес посылки");
        orderWeightEnumComboBox.setItems(EnumSet.allOf(OrderWeightEnum.class));

        orderFragilityEnumComboBox = new ComboBox<>("Хрупкость посылки");
        orderFragilityEnumComboBox.setItems(EnumSet.allOf(OrderFragilityEnum.class));

        orderValueEnumComboBox = new ComboBox<>("Ценность посылки");
        orderValueEnumComboBox.setItems(EnumSet.allOf(OrderValueEnum.class));

        addressField = new TextField("Адрес доставки");

        startDatePicker = new DatePicker("Срок доставки (начало)");
        endDatePicker = new DatePicker("Срок доставки (конец)");

        saveOrder = new Button("Сохранить");

        orderForm.add(orderSizeEnumComboBox,
                orderWeightEnumComboBox,
                orderFragilityEnumComboBox,
                orderValueEnumComboBox,
                addressField,
                startDatePicker,
                endDatePicker,
                saveOrder);

        setContent(orderForm);

        fillForm();
    }

    public void fillForm() {
        this.saveOrder.addClickListener(clickEvent -> {
            // Создадим объект контакта получив значения с формы
            final ClientOrder clientOrder = new ClientOrder();

            clientOrder.setId(UUID.randomUUID().toString());
            clientOrder.setAddress(this.addressField.getValue().trim());
            clientOrder.setOrderFragilityEnum(this.orderFragilityEnumComboBox.getValue());
            clientOrder.setOrderSizeEnum(this.orderSizeEnumComboBox.getValue());
            clientOrder.setOrderValueEnum(this.orderValueEnumComboBox.getValue());
            clientOrder.setOrderWeightEnum(this.orderWeightEnumComboBox.getValue());
            clientOrder.setDeliveryDateStart(this.startDatePicker.getValue());
            clientOrder.setDeliveryDateEnd(this.endDatePicker.getValue());
            clientOrder.setNew(true);

            orderService.save(clientOrder);

            Notification notification = new Notification("Заказ успешно создан", 1000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addDetachListener(detachEvent -> UI.getCurrent().navigate(OrderController.class));
            this.orderForm.setEnabled(false);
            notification.open();
        });
    }
}
