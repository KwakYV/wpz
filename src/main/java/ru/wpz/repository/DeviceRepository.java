package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.wpz.dto.DeviceBusyDto;
import ru.wpz.dto.DeviceFullInfoDto;
import ru.wpz.dto.DeviceStatusDto;
import ru.wpz.dto.ReportMomentDto;
import ru.wpz.entity.Device;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query(nativeQuery = true)
    List<DeviceStatusDto> findDevicesWithLastStatus(Long zoneId);

    @Query(nativeQuery = true)
    ReportMomentDto findBusyReportAtMoment(Long buildingId);

    @Query(nativeQuery = true)
    List<DeviceBusyDto> findDeviceBusyTimeByPeriod(LocalDateTime start, LocalDateTime end, Long buildingId);

    @Query(nativeQuery = true)
    List<DeviceBusyDto> findDeviceBusyTimeByDay(LocalDateTime day, Long buildingId);

    @Query(nativeQuery = true)
    DeviceFullInfoDto getDeviceFullInfo(Long devId);

    @Query(nativeQuery = true)
    DeviceStatusDto findDeviceStatusById(Long deviceId);
}
