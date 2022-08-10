package ru.wpz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.DeviceDto;
import ru.wpz.dto.DeviceStatusDto;
import ru.wpz.mapper.DeviceMapper;
import ru.wpz.service.DeviceService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/device")
@AllArgsConstructor
@Api(value = "device", produces = "Контроллер для датчиков")
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class DeviceController {

    private final DeviceMapper deviceMapper;
    private final DeviceService deviceService;

    @GetMapping("/zone/{zoneId}")
    @ApiOperation("Получение всех датчиков по зоне парковки")
    public List<DeviceStatusDto> getDevicesWithLastStatus(@PathVariable(name="zoneId") Long zoneId){
        return deviceService.getAll(zoneId);
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

    @PutMapping("")
    @ApiOperation("Обновление информации по  датчику")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody DeviceDto deviceDto){
        deviceService.save(deviceMapper.mapDevice(deviceDto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление датчика по id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        deviceService.delete(id);
    }
}
