package ru.autkaev.deliveryapp.order.domain;

/**
 * Ценность товара.
 *
 * @author Anton Utkaev
 */
public enum OrderValueEnum {

    NONE("Нет ценности"), VALUABLE("Ценный"), EXTRA_VALUABLE("Очень ценный");

    private final String label;

    OrderValueEnum(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
