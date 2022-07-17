package ru.wpz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.DeviceDto;
import ru.wpz.mapper.DeviceMapper;
import ru.wpz.service.DeviceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/device")
@AllArgsConstructor
@Api(value = "device", produces = "Контроллер для датчиков")
public class DeviceController {

    private final DeviceMapper deviceMapper;
    private final DeviceService deviceService;

    @GetMapping("/{zoneId}")
    @ApiOperation("Получение всех датчиков по зоне парковки")
    public List<DeviceDto> showAll(@PathVariable int zoneId){
        return deviceService.getAll(zoneId).stream()
                .map(deviceMapper::mapDeviceDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation("Поиск датчика по id")
    public DeviceDto get(@PathVariable long id){
        return deviceMapper.mapDeviceDto(deviceService.get(id).orElse(null));
    }

    @PostMapping
    @ApiOperation("Сохранение нового датчика")
    public void save(@RequestBody DeviceDto device){
        deviceService.save(deviceMapper.mapDevice(device));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление датчика по id")
    public void delete(@PathVariable Long id) {
        deviceService.delete(id);
    }
}
