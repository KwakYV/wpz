package ru.wpz.lessons.lesson1.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.wpz.lessons.lesson1.dao.EdgeDeviceDao;
import ru.wpz.lessons.lesson1.entity.EdgeDevice;

import java.util.List;

/**
 * Service for Device
 */

@Slf4j
@AllArgsConstructor
@Service
public class EdgeDeviceService {
    private EdgeDeviceDao edgeDeviceDao;

    public EdgeDevice save(EdgeDevice edgeDevice){
        return edgeDeviceDao.save(edgeDevice);
    }

    public void deleteById(Long id) {
        edgeDeviceDao.deleteById(id);
    }

    public List<EdgeDevice> getDeviceList() {
        return edgeDeviceDao.findAll();
    }
}
