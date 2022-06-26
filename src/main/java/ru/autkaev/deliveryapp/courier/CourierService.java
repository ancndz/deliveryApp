package ru.autkaev.deliveryapp.courier;

import ru.autkaev.deliveryapp.courier.domain.Courier;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с курьерами.
 *
 * @author Anton Utkaev
 */
public interface CourierService {

    Optional<Courier> findById(String id);

    List<Courier> findAll();

    void deleteById(String id);

    void save(Courier courier);
}
