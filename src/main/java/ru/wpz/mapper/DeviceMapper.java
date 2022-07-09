package ru.wpz.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.wpz.dto.DeviceDto;
import ru.wpz.entity.Device;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public abstract class DeviceMapper {

    public abstract DeviceDto mapDeviceDto(Device device);

    public abstract Device mapDevice(DeviceDto deviceDto);
}
