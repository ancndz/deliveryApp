package ru.autkaev.deliveryapp.delivery;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import ru.autkaev.deliveryapp.delivery.domain.Delivery;

/**
 * Сервис для работы с доставками.
 *
 * @author Anton Utkaev
 */
public interface DeliveryService {
    Optional<Delivery> findById(String id);

    List<Delivery> findAll();

    void deleteById(String id);

    void save(Delivery delivery);
}
