package ru.wpz.lessons.lesson1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.wpz.lessons.lesson1.mapper.MapperView;
import ru.wpz.lessons.lesson1.service.BuildingService;

@RestController
@RequestMapping("/building")
public class BuildingController {

    private final MapperView mapperView;
    private final BuildingService buildingService;

    public BuildingController(MapperView mapperView, BuildingService buildingService) {
        this.mapperView = mapperView;
        this.buildingService = buildingService;
    }
}
