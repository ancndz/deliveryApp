package ru.autkaev.deliveryapp.order.domain;

/**
 * Габариты товара.
 *
 * @author Anton Utkaev
 */
public enum OrderSizeEnum {

    SMALL("Мелкий"), MEDIUM("Средний"), LARGE("Крупный");

    private final String label;

    OrderSizeEnum(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
