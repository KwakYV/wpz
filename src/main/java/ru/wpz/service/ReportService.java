package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wpz.dto.DayReportDto;
import ru.wpz.dto.ReportMomentDto;
import ru.wpz.entity.*;
import ru.wpz.repository.BuildingRepository;
import ru.wpz.repository.DeviceRepository;
import ru.wpz.repository.OrganizationRepository;
import ru.wpz.repository.ReportRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final OrganizationRepository organizationRepository;
    private final BuildingRepository buildingRepository;
    private final DeviceRepository deviceRepository;

    public void createReport(Device device, Message message) {
        Report report = new Report();
            report.setTimeMessage(message.getCreatedDt());
            report.setStatus(message.getStatus());
            report.setDevice(device);
            report.setZoneId(device.getZoneId());
            report.setBuilding(device.getZoneId().getBuilding());
            report.setOrganizationId(device.getZoneId().getBuilding().getOrganization());
            reportRepository.save(report);
    }

    public List<Device> find(int zoneId){
        return deviceRepository.findAll(zoneId).stream()
                .map(device -> {device.getMessages().subList(0,device.getMessages().size() - 1).clear(); return device;})
                .collect(Collectors.toList());
    }

    public ReportMomentDto findDevOrg(long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        List<Device> deviceList = new ArrayList<>();
        if (organization != null){
            for (Building building : organization.getBuildings()) {
                for (Parking parking: building.getParking()) {
                    deviceList.addAll(find(parking.getZoneNumber()));
                }
            }
        }
        return createReportMoment(deviceList);
    }

    public ReportMomentDto createReportMoment(List<Device> list){
        ReportMomentDto reportMomentDto = new ReportMomentDto();
        reportMomentDto.setTotal(list.size());
        reportMomentDto.setStatusTaken((int) list.stream().filter(device -> device.getMessages().get(0).getStatus() == 1).count());
        reportMomentDto.setStatusFree((int) list.stream().filter(device -> device.getMessages().get(0).getStatus() == 0).count());
        reportMomentDto.setPercent((double) Math.floorDiv(reportMomentDto.getStatusTaken() * 100, reportMomentDto.getTotal()));
        return reportMomentDto;
    }

    public DayReportDto findDayDevOrg(long id, LocalDateTime day) {
        return null; // пока null
    }
}
