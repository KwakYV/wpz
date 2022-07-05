package ru.wpz.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.wpz.dto.ParkingDto;
import ru.wpz.entity.Parking;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public abstract class ParkingMapper {

    public abstract ParkingDto mapParkingDto(Parking parking);

    public abstract Parking mapParking(ParkingDto parkingDto);
}
