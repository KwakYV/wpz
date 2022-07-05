package ru.wpz.controller;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.BuildingDto;
import ru.wpz.mapper.BuildingMapper;
import ru.wpz.service.BuildingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1//building")
@AllArgsConstructor
public class BuildingController {

    private final BuildingMapper buildingMapper;
    private final BuildingService buildingService;

    @GetMapping()
    public List<BuildingDto> showAllBuilding(){
        return buildingService.getAllBuilding().stream()
                .map(buildingMapper::mapBuildingDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BuildingDto getBuilding(@PathVariable long id){
        return buildingMapper.mapBuildingDto(buildingService.getBuilding(id).orElse(null));
    }

    @PostMapping("/add")
    @Transactional
    public BuildingDto saveNewBuilding(@RequestBody BuildingDto building){
        buildingService.saveBuilding(buildingMapper.mapBuilding(building));
        return building;
    }

    @PostMapping("/delete/{id}")
    public void deleteBuilding(@PathVariable Long id) {
        buildingService.deleteBuilding(id);
    }
}
