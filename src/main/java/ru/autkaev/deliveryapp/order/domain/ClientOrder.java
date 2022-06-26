package ru.autkaev.deliveryapp.order.domain;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.autkaev.deliveryapp.delivery.domain.Delivery;

import java.time.LocalDate;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Доменная модель заказа.
 *
 * @author Anton Utkaev
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ClientOrder {

    @Id
    @NotNull
    private String id;

    @Enumerated(EnumType.ORDINAL)
    private OrderSizeEnum orderSizeEnum;

    @Enumerated(EnumType.ORDINAL)
    private OrderWeightEnum orderWeightEnum;

    @Enumerated(EnumType.ORDINAL)
    private OrderFragilityEnum orderFragilityEnum;

    @Enumerated(EnumType.ORDINAL)
    private OrderValueEnum orderValueEnum;

    @Column
    private String address;

    @Column
    private LocalDate deliveryDateStart;

    @Column
    private LocalDate deliveryDateEnd;

    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    private Delivery delivery;

    @Column
    private boolean isNew = false;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public OrderSizeEnum getOrderSizeEnum() {
        return orderSizeEnum;
    }

    public void setOrderSizeEnum(OrderSizeEnum orderSizeEnum) {
        this.orderSizeEnum = orderSizeEnum;
    }

    public OrderWeightEnum getOrderWeightEnum() {
        return orderWeightEnum;
    }

    public void setOrderWeightEnum(OrderWeightEnum orderWeightEnum) {
        this.orderWeightEnum = orderWeightEnum;
    }

    public OrderFragilityEnum getOrderFragilityEnum() {
        return orderFragilityEnum;
    }

    public void setOrderFragilityEnum(OrderFragilityEnum orderFragilityEnum) {
        this.orderFragilityEnum = orderFragilityEnum;
    }

    public OrderValueEnum getOrderValueEnum() {
        return orderValueEnum;
    }

    public void setOrderValueEnum(OrderValueEnum orderValueEnum) {
        this.orderValueEnum = orderValueEnum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDeliveryDateStart() {
        return deliveryDateStart;
    }

    public void setDeliveryDateStart(LocalDate deliveryDateStart) {
        if (deliveryDateStart != null && this.deliveryDateEnd != null) {
            if (deliveryDateStart.isAfter(this.deliveryDateEnd)) {
                throw new ContextedRuntimeException("Start date cannot be after end date!")
                        .addContextValue("start date", deliveryDateStart)
                        .addContextValue("end date", this.deliveryDateEnd);
            }
        }
        this.deliveryDateStart = deliveryDateStart;
    }

    public LocalDate getDeliveryDateEnd() {
        return deliveryDateEnd;
    }

    public void setDeliveryDateEnd(LocalDate deliveryDateEnd) {
        if (deliveryDateEnd != null && this.deliveryDateStart != null) {
            if (deliveryDateEnd.isBefore(this.deliveryDateStart)) {
                throw new ContextedRuntimeException("End date cannot be before start date!")
                        .addContextValue("start date", this.deliveryDateStart)
                        .addContextValue("end date", deliveryDateEnd);
            }
        }
        this.deliveryDateEnd = deliveryDateEnd;
    }

    @Nullable
    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(@Nullable Delivery delivery) {
        this.delivery = delivery;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public String toString() {
        return "ClientOrder {" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ClientOrder clientOrder = (ClientOrder) o;
        return id.equals(clientOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
