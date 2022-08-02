package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wpz.dao.DeviceInfoDao;
import ru.wpz.entity.Device;
import ru.wpz.entity.DeviceStatus;
import ru.wpz.repository.DeviceRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceInfoDao deviceInfoDao;

    public List<DeviceStatus> getAll(Long zoneId) {
        return deviceInfoDao.getDevicesWithLastStatus(zoneId);
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
