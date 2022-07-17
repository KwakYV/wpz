package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wpz.entity.Building;
import ru.wpz.repository.BuildingRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;

    public List<Building> getAll() {
        return buildingRepository.findAll();
    }

    public Optional<Building> get(long id) {
        return buildingRepository.findById(id);
    }

    @Transactional
    public void save(Building building) {
        buildingRepository.save(building);
    }

    public void delete(Long id) {
        buildingRepository.deleteById(id);
    }
}
