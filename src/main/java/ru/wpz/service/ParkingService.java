package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wpz.entity.Parking;
import ru.wpz.repository.ParkingRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public List<Parking> getAllParking() {
        return parkingRepository.findAll();
    }

    public Optional<Parking> getParking(long id) {
        return  parkingRepository.findById(id);
    }

    public void saveParking(Parking parking) {
        parkingRepository.save(parking);
    }

    public void deleteParking(Long id) {
        parkingRepository.deleteById(id);
    }
}
