package ru.autkaev.deliveryapp.order.domain;

/**
 * Прочность товара.
 *
 * @author Anton Utkaev
 */
public enum OrderFragilityEnum {

    NONE("Прочный"), FRAGILE("Хрупкий"), EXTRA_FRAGILE("Очень хрупкий");

    private final String label;

    OrderFragilityEnum(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
