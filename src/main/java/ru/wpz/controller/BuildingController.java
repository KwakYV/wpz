package ru.wpz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.BuildingDto;
import ru.wpz.mapper.BuildingMapper;
import ru.wpz.service.BuildingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/building")
@AllArgsConstructor
@Api(value = "building", produces = "Контроллер для объектов")
@CrossOrigin(origins = "http://127.0.0.1:3000")
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

    @GetMapping("/org/{orgId}")
    @ApiOperation("Получение списка объектов по организации")
    public List<BuildingDto> findAllByOrg(@PathVariable(name="orgId") Long orgId) {
        return buildingService.getByOrg(orgId).stream()
                .map(buildingMapper::mapBuildingDto).collect(Collectors.toList());

    }

    @PostMapping
    @ApiOperation("Сохранение нового объекта")
    public void save(@RequestBody BuildingDto building){
        buildingService.save(buildingMapper.mapBuilding(building));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление объекта по id")
    public void delete(@PathVariable Long id) {
        buildingService.delete(id);
    }
}
