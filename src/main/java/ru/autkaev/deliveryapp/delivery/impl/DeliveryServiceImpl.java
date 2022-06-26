package ru.autkaev.deliveryapp.delivery.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.autkaev.deliveryapp.delivery.DeliveryService;
import ru.autkaev.deliveryapp.delivery.domain.Delivery;
import ru.autkaev.deliveryapp.delivery.domain.repo.DeliveryRepository;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с доставками.
 *
 * @author Anton Utkaev
 * @since 2022.06.26
 */
@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(final DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Optional<Delivery> findById(String id) {
        return deliveryRepository.findById(id);
    }


    @Override
    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        deliveryRepository.deleteById(id);
    }

    @Override
    public void save(Delivery delivery) {
        deliveryRepository.save(delivery);
    }

}
