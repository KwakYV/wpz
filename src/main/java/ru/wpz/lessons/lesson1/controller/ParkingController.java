package ru.wpz.lessons.lesson1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.wpz.lessons.lesson1.mapper.MapperView;
import ru.wpz.lessons.lesson1.service.ParkingService;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final MapperView mapperView;
    private final ParkingService parkingService;

    public ParkingController(MapperView mapperView, ParkingService parkingService) {
        this.mapperView = mapperView;
        this.parkingService = parkingService;
    }
}
