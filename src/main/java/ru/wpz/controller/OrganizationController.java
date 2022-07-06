package ru.wpz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.OrganizationDto;
import ru.wpz.mapper.OrganizationMapper;
import ru.wpz.service.OrganizationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/organization")
@AllArgsConstructor
@Api(value = "organization", produces = "Контроллер для организаций")
public class OrganizationController {

    private final OrganizationMapper organizationMapper;
    private final OrganizationService organizationService;

    @GetMapping()
    @ApiOperation("Получение всех организаций")
    public List<OrganizationDto> showAll(){
        return organizationService.getAll().stream()
                .map(organizationMapper::mapOrganizationDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation("Поиск организации по id")
    public OrganizationDto get(@PathVariable long id){
        return organizationMapper.mapOrganizationDto(organizationService.get(id).orElse(null));
    }

    @PostMapping("/add")
    @Transactional
    @ApiOperation("Сохранение новой организации")
    public OrganizationDto save(@RequestBody OrganizationDto organization){
        organizationService.save(organizationMapper.mapOrganization(organization));
        return organization;
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("Удаление организации по id")
    public void delete(@PathVariable Long id) {
        organizationService.delete(id);
    }
}
