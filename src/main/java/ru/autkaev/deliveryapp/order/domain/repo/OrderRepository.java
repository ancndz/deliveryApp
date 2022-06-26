package ru.autkaev.deliveryapp.order.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.autkaev.deliveryapp.order.domain.ClientOrder;

/**
 * Репозиторий для работы с заказами.
 *
 * @author Anton Utkaev
 */
@Repository
public interface OrderRepository extends JpaRepository<ClientOrder, String> {
}
