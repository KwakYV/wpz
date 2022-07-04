package ru.wpz.lessons.lesson1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wpz.lessons.lesson1.entity.Parking;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
}
