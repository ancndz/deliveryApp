package ru.autkaev.deliveryapp.courier.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.autkaev.deliveryapp.courier.CourierService;
import ru.autkaev.deliveryapp.courier.domain.Courier;
import ru.autkaev.deliveryapp.courier.domain.repo.CourierRepository;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с курьерами.
 *
 * @author Anton Utkaev
 * @since 2022.06.26
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    public CourierServiceImpl(final CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    public Optional<Courier> findById(String id) {
        return courierRepository.findById(id);
    }

    @Override
    public List<Courier> findAll() {
        return courierRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        courierRepository.deleteById(id);
    }

    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }
}
