package ru.wpz.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.wpz.dto.OrganizationDto;
import ru.wpz.entity.Organization;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public abstract class OrganizationMapper {

    public abstract OrganizationDto mapOrganizationDto(Organization organization);

    public abstract Organization mapOrganization(OrganizationDto organizationDto);
}
