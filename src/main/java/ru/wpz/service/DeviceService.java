package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wpz.entity.Device;
import ru.wpz.repository.DeviceRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public List<Device> getAllDevice() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDevice(long id) {
        return deviceRepository.findById(id);
    }

    public void saveDevice(Device device) {
        deviceRepository.save(device);
    }

    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }
}
