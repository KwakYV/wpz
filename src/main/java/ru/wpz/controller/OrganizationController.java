package ru.wpz.controller;

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
public class OrganizationController {

    private final OrganizationMapper organizationMapper;
    private final OrganizationService organizationService;

    @GetMapping()
    public List<OrganizationDto> showAllOrganization(){
        return organizationService.getAllOrganization().stream()
                .map(organizationMapper::mapOrganizationDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrganizationDto getOrganization(@PathVariable long id){
        return organizationMapper.mapOrganizationDto(organizationService.getOrganization(id).orElse(null));
    }

    @PostMapping("/add")
    @Transactional
    public OrganizationDto saveNewOrganization(@RequestBody OrganizationDto organization){
        organizationService.saveOrganization(organizationMapper.mapOrganization(organization));
        return organization;
    }

    @PostMapping("/delete/{id}")
    public void deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
    }
}
