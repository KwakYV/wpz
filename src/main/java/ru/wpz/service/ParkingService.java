package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wpz.entity.Parking;
import ru.wpz.repository.ParkingRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public List<Parking> getAll() {
        return parkingRepository.findAll();
    }

    public Optional<Parking> get(long id) {
        return  parkingRepository.findById(id);
    }

    @Transactional
    public void save(Parking parking) {
        parkingRepository.save(parking);
    }

    public void delete(Long id) {
        parkingRepository.deleteById(id);
    }
}
