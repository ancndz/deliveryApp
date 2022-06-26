package ru.autkaev.deliveryapp.delivery.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.autkaev.deliveryapp.delivery.domain.Delivery;

/**
 * Репозиторий для работы с доставкой.
 *
 * @author Anton Utkaev
 */
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, String> {
}
