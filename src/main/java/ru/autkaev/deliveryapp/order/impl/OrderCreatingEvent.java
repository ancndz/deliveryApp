package ru.autkaev.deliveryapp.order.impl;

import org.springframework.context.ApplicationEvent;
import ru.autkaev.deliveryapp.order.domain.ClientOrder;

/**
 * Событие создания заказа.
 *
 * @author Anton Utkaev
 */
public class OrderCreatingEvent extends ApplicationEvent {

    private final ClientOrder clientOrder;

    public OrderCreatingEvent(Object source, ClientOrder clientOrder) {
        super(source);
        this.clientOrder = clientOrder;
    }

    public ClientOrder getClientOrder() {
        return clientOrder;
    }
}
