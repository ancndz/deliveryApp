package ru.autkaev.deliveryapp.courier.domain;

/**
 * Опыт работы курьера.
 *
 * @author Anton Utkaev
 */
public enum CourierExperienceEnum {

    LOW("Мало опыта"),

    MEDIUM("Средний опыт"),

    HIGH("Много опыта");

    private final String label;

    CourierExperienceEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
