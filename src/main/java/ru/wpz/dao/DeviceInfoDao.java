package ru.wpz.dao;

import ru.wpz.entity.DeviceStatus;

import java.util.List;

public interface DeviceInfoDao {
    public List<DeviceStatus> getDevicesWithLastStatus(Long zoneId);
}
