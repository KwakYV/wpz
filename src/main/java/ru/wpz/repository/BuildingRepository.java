package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wpz.entity.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
}
