package ru.autkaev.deliveryapp.delivery.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.autkaev.deliveryapp.courier.domain.Courier;
import ru.autkaev.deliveryapp.order.domain.ClientOrder;
import ru.autkaev.deliveryapp.order.domain.OrderFragilityEnum;
import ru.autkaev.deliveryapp.order.domain.OrderSizeEnum;
import ru.autkaev.deliveryapp.order.domain.OrderValueEnum;
import ru.autkaev.deliveryapp.order.domain.OrderWeightEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * Созданная доставка.
 *
 * @author Anton Utkaev
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Delivery {

    @Id
    @NotNull
    private String id;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ClientOrder> clientOrders = new ArrayList<>();

    @Column
    @NotNull
    private String address;

    @Enumerated(EnumType.ORDINAL)
    private OrderSizeEnum maxOrderSize;

    @Enumerated(EnumType.ORDINAL)
    private OrderFragilityEnum maxOrderFragility;

    @Enumerated(EnumType.ORDINAL)
    private OrderValueEnum maxOrderValue;

    @Enumerated(EnumType.ORDINAL)
    private OrderWeightEnum maxOrderWeight;

    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    private Courier courier;

    private boolean isCompleted;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createDate;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<ClientOrder> getClientOrders() {
        return clientOrders;
    }

    public void setClientOrders(List<ClientOrder> clientOrders) {
        this.clientOrders = clientOrders;
    }

    @Nullable
    public Courier getCourier() {
        return courier;
    }

    public void setCourier(@Nullable Courier courier) {
        this.courier = courier;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderSizeEnum getMaxOrderSize() {
        return maxOrderSize;
    }

    public void setMaxOrderSize(OrderSizeEnum maxOrderSize) {
        this.maxOrderSize = maxOrderSize;
    }

    public OrderFragilityEnum getMaxOrderFragility() {
        return maxOrderFragility;
    }

    public void setMaxOrderFragility(OrderFragilityEnum maxOrderFragility) {
        this.maxOrderFragility = maxOrderFragility;
    }

    public OrderValueEnum getMaxOrderValue() {
        return maxOrderValue;
    }

    public void setMaxOrderValue(OrderValueEnum maxOrderValue) {
        this.maxOrderValue = maxOrderValue;
    }

    public OrderWeightEnum getMaxOrderWeight() {
        return maxOrderWeight;
    }

    public void setMaxOrderWeight(OrderWeightEnum maxOrderWeight) {
        this.maxOrderWeight = maxOrderWeight;
    }

    @Override
    public String toString() {
        return "Delivery {" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Delivery delivery = (Delivery) o;
        return id.equals(delivery.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
