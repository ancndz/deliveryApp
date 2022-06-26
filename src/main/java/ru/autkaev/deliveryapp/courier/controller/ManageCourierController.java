package ru.autkaev.deliveryapp.courier.controller;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.autkaev.deliveryapp.courier.CourierService;
import ru.autkaev.deliveryapp.courier.domain.Courier;
import ru.autkaev.deliveryapp.courier.domain.CourierExperienceEnum;
import ru.autkaev.deliveryapp.courier.domain.CourierVehicleEnum;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

/**
 * Контроллер для работы с курьерами.
 *
 * @author Anton Utkaev
 */
@Route("manage-courier")
public class ManageCourierController extends AppLayout implements HasUrlParameter<String> {

    private final CourierService courierService;

    private String courierId;

    private final FormLayout courierForm;

    private final TextField nameField;

    private final ComboBox<CourierExperienceEnum> experienceEnumComboBox;

    private final ComboBox<CourierVehicleEnum> vehicleEnumComboBox;

    private final Button saveCourier;

    public ManageCourierController(final CourierService courierService) {
        this.courierService = courierService;

        courierForm = new FormLayout();
        nameField = new TextField("Имя");

        experienceEnumComboBox = new ComboBox<>("Опыт работы");
        experienceEnumComboBox.setItems(EnumSet.allOf(CourierExperienceEnum.class));

        vehicleEnumComboBox = new ComboBox<>("Средство передвижения");
        vehicleEnumComboBox.setItems(EnumSet.allOf(CourierVehicleEnum.class));

        saveCourier = new Button("Сохранить");

        courierForm.add(nameField, experienceEnumComboBox, vehicleEnumComboBox, saveCourier);

        setContent(courierForm);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String courierId) {
        this.courierId = courierId;
        if (!"new".equals(courierId)) {
            addToNavbar(new H3("Редактирование курьера"));
            fillForm(false); // Заполнение формы
        } else {
            addToNavbar(new H3("Создание курьера"));
            fillForm(true); // Заполнение формы
        }
    }

    public void fillForm(boolean isCreation) {
        if (!isCreation) {
            Optional<Courier> contact = courierService.findById(courierId);
            contact.ifPresent(courier -> {
                this.nameField.setValue(courier.getName());
                this.experienceEnumComboBox.setValue(courier.getCourierExperienceEnum());
                this.vehicleEnumComboBox.setValue(courier.getCourierVehicleEnum());
            });
        }
        this.saveCourier.addClickListener(clickEvent -> {
            // Создадим объект контакта получив значения с формы
            final Courier courier = new Courier();
            if (isCreation) {
                this.courierId = UUID.randomUUID().toString();
            }
            courier.setId(this.courierId);
            courier.setName(nameField.getValue().trim());
            courier.setCourierExperienceEnum(experienceEnumComboBox.getValue());
            courier.setCourierVehicleEnum(vehicleEnumComboBox.getValue());
            courierService.save(courier);

            Notification notification =
                    new Notification(isCreation ? "Курьер успешно создан" : "Курьер был изменен", 1000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addDetachListener(detachEvent -> UI.getCurrent().navigate(CourierController.class));
            this.courierForm.setEnabled(false);
            notification.open();
        });
    }
}
