package ru.wpz.lessons.lesson1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.wpz.lessons.lesson1.mapper.MapperView;
import ru.wpz.lessons.lesson1.service.DeviceService;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final MapperView mapperView;
    private final DeviceService deviceService;

    public DeviceController(MapperView mapperView, DeviceService deviceService) {
        this.mapperView = mapperView;
        this.deviceService = deviceService;
    }
}
