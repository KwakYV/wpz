package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wpz.entity.Parking;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
}
