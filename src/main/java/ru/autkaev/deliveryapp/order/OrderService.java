package ru.autkaev.deliveryapp.order;

import org.springframework.transaction.annotation.Transactional;
import ru.autkaev.deliveryapp.order.domain.ClientOrder;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с заказами.
 *
 * @author Anton Utkaev
 */
public interface OrderService {

    Optional<ClientOrder> findById(String id);

    List<ClientOrder> findAll();

    void deleteById(String id);

    void save(ClientOrder clientOrder);
}
