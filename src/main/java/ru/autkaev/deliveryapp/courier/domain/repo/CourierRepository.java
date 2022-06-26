package ru.autkaev.deliveryapp.courier.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.autkaev.deliveryapp.courier.domain.Courier;

/**
 * Репозиторий для работы с курьерами.
 *
 * @author Anton Utkaev
 */
@Repository
public interface CourierRepository extends JpaRepository<Courier, String> {
}
