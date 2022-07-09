package ru.wpz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "building", produces = "Контроллер для объектов")
public class BuildingController {

    private final BuildingMapper buildingMapper;
    private final BuildingService buildingService;

    @GetMapping()
    @ApiOperation("Получение всех объектов")
    public List<BuildingDto> showAll(){
        return buildingService.getAll().stream()
                .map(buildingMapper::mapBuildingDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation("Поиск объекта по id")
    public BuildingDto get(@PathVariable long id){
        return buildingMapper.mapBuildingDto(buildingService.get(id).orElse(null));
    }

    @PostMapping("/add")
    @Transactional
    @ApiOperation("Сохранение нового объекта")
    public BuildingDto save(@RequestBody BuildingDto building){
        buildingService.save(buildingMapper.mapBuilding(building));
        return building;
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("Удаление объекта по id")
    public void delete(@PathVariable Long id) {
        buildingService.delete(id);
    }
}
