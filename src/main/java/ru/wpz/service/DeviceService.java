package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wpz.dto.DeviceStatusDto;
import ru.wpz.entity.Device;
import ru.wpz.repository.DeviceRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public List<DeviceStatusDto> getAll(Long zoneId) {
        return deviceRepository.findDevicesWithLastStatus(zoneId);
    }

    public Optional<Device> get(long id) {
        return deviceRepository.findById(id);
    }

    @Transactional
    public void save(Device device) {
        deviceRepository.save(device);
    }

    public void delete(Long id) {
        deviceRepository.deleteById(id);
    }
}
