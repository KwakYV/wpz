package ru.wpz.controller;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.DeviceDto;
import ru.wpz.mapper.DeviceMapper;
import ru.wpz.service.DeviceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1//device")
@AllArgsConstructor
public class DeviceController {

    private final DeviceMapper deviceMapper;
    private final DeviceService deviceService;

    @GetMapping()
    public List<DeviceDto> showAllDevice(){
        return deviceService.getAllDevice().stream()
                .map(deviceMapper::mapDeviceDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DeviceDto getDevice(@PathVariable long id){
        return deviceMapper.mapDeviceDto(deviceService.getDevice(id).orElse(null));
    }

    @PostMapping("/add")
    @Transactional
    public DeviceDto saveNewDevice(@RequestBody DeviceDto device){
        deviceService.saveDevice(deviceMapper.mapDevice(device));
        return device;
    }

    @PostMapping("/delete/{id}")
    public void deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
    }
}
