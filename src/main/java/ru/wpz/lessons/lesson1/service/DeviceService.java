package ru.wpz.lessons.lesson1.service;

import org.springframework.stereotype.Service;
import ru.wpz.lessons.lesson1.repository.DeviceRepository;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
}
