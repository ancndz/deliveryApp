package ru.autkaev.deliveryapp.courier.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.autkaev.deliveryapp.delivery.domain.Delivery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * Доменная модель курьера.
 *
 * @author Anton Utkaev
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Courier {

    @Id
    @NotNull
    private String id;

    @Column
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private CourierExperienceEnum courierExperienceEnum;

    @Enumerated(EnumType.ORDINAL)
    private CourierVehicleEnum courierVehicleEnum;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Delivery> activeDeliveries = new ArrayList<>();

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createDate;

    public void setId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public CourierExperienceEnum getCourierExperienceEnum() {
        return courierExperienceEnum;
    }

    public void setCourierExperienceEnum(final CourierExperienceEnum courierExperienceEnum) {
        this.courierExperienceEnum = courierExperienceEnum;
    }

    public CourierVehicleEnum getCourierVehicleEnum() {
        return courierVehicleEnum;
    }

    public void setCourierVehicleEnum(final CourierVehicleEnum courierVehicleEnum) {
        this.courierVehicleEnum = courierVehicleEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Delivery> getActiveDeliveries() {
        return activeDeliveries;
    }

    public void setActiveDeliveries(List<Delivery> activeDeliveries) {
        this.activeDeliveries = activeDeliveries;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Courier courier = (Courier) o;
        return id.equals(courier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Courier {" + id + '}';
    }
}
