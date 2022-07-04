package ru.wpz.lessons.lesson1.service;

import org.springframework.stereotype.Service;
import ru.wpz.lessons.lesson1.mapper.MapperView;
import ru.wpz.lessons.lesson1.repository.ParkingRepository;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }
}
