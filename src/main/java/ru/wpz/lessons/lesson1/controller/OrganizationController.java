package ru.wpz.lessons.lesson1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.wpz.lessons.lesson1.mapper.MapperView;
import ru.wpz.lessons.lesson1.service.OrganizationService;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private final MapperView mapperView;
    private final OrganizationService organizationService;

    public OrganizationController(MapperView mapperView, OrganizationService organizationService) {
        this.mapperView = mapperView;
        this.organizationService = organizationService;
    }
}
