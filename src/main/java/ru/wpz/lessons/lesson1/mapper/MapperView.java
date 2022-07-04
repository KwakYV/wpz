package ru.wpz.lessons.lesson1.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.wpz.lessons.lesson1.dto.*;
import ru.wpz.lessons.lesson1.entity.*;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public abstract class MapperView {

    public abstract OrganizationDto mapOrganizationDto(Organization organization);

    public abstract BuildingDto mapOrganizationDto(Building building);

    public abstract ParkingDto mapOrganizationDto(Parking parking);

    public abstract DeviceDto mapOrganizationDto(Device device);

    public abstract MessageDto mapOrganizationDto(Message message);
}
