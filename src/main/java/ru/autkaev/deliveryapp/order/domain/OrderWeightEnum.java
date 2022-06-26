package ru.autkaev.deliveryapp.order.domain;

/**
 * Вес товара.
 *
 * @author Anton Utkaev
 */
public enum OrderWeightEnum {

    SMALL("Легкий"), MEDIUM("Средний"), LARGE("Тяжелый");

    private final String label;

    OrderWeightEnum(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
