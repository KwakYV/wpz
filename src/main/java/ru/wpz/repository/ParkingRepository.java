package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wpz.entity.Parking;

import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    public Optional<Parking> findParkingByBuilding(Long id);
}
