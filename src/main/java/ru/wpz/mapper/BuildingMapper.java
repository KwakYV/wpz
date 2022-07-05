package ru.wpz.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.wpz.dto.BuildingDto;
import ru.wpz.entity.Building;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public abstract class BuildingMapper {

    public abstract BuildingDto mapBuildingDto(Building building);

    public abstract Building mapBuilding(BuildingDto buildingDto);
}
