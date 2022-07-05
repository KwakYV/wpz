package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wpz.entity.Building;
import ru.wpz.repository.BuildingRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;

    public List<Building> getAllBuilding() {
        return buildingRepository.findAll();
    }

    public Optional<Building> getBuilding(long id) {
        return buildingRepository.findById(id);
    }

    public void saveBuilding(Building building) {
        buildingRepository.save(building);
    }

    public void deleteBuilding(Long id) {
        buildingRepository.deleteById(id);
    }
}
