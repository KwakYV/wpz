package ru.wpz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.ParkingDto;
import ru.wpz.mapper.ParkingMapper;
import ru.wpz.service.ParkingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/parking")
@AllArgsConstructor
@Api(value = "parking", produces = "Контроллер для парковок")
public class ParkingController {

    private final ParkingMapper parkingMapper;
    private final ParkingService parkingService;

    @GetMapping()
    @ApiOperation("Получение всех парковок")
    public List<ParkingDto> showAll(){
        return parkingService.getAll().stream()
                .map(parkingMapper::mapParkingDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation("Поиск парковки по id")
    public ParkingDto get(@PathVariable long id){
        return parkingMapper.mapParkingDto(parkingService.get(id).orElse(null));
    }

    @PostMapping
    @ApiOperation("Сохранение новой парковки")
    public void save(@RequestBody ParkingDto parking){
        parkingService.save(parkingMapper.mapParking(parking));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление парковки по id")
    public void delete(@PathVariable Long id) {
        parkingService.delete(id);
    }
}
