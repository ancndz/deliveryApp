package ru.autkaev.deliveryapp.courier.domain;

/**
 * Средство передвижения курьера.
 *
 * @author Anton Utkaev
 */
public enum CourierVehicleEnum {

    NONE("Нет"),

    MOTO("Мототранспорт"),

    AUTO("Автомобиль"),

    HEAVY_AUTO("Грузовик");

    private final String label;

    CourierVehicleEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
