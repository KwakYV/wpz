package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wpz.dto.DayReportDto;
import ru.wpz.dto.ReportMomentDto;
import ru.wpz.dto.ReportPeriodDto;
import ru.wpz.entity.*;
import ru.wpz.repository.BuildingRepository;
import ru.wpz.repository.DeviceRepository;
import ru.wpz.repository.OrganizationRepository;
import ru.wpz.repository.ReportRepository;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;
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

    public ReportMomentDto findDevOrg(long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        List<Device> deviceList = getAllDevice(organization);
        return createReportMoment(deviceList);
    }

    public List<DayReportDto> findDayDevOrg(long id, LocalDateTime day) {
        LocalDateTime start = day.with(ChronoField.NANO_OF_DAY, LocalTime.MIN.toNanoOfDay());
        LocalDateTime end = day.with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay());
        Organization organization = organizationRepository.findById(id).orElse(null);
        List<Report> reportList = getReportOnOrg(organization, start, end);
        List<Device> deviceList = getAllDevice(organization);
        List<DayReportDto> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            DayReportDto dayReportDto = new DayReportDto();
            dayReportDto.setNumber(i);
            dayReportDto.setNumber(deviceList.size());
            dayReportDto.setPercent(findReportPeriod(deviceList, reportList, start, start.plusHours(1L)).getPercent());
        }
        return list;
    }

    public ReportPeriodDto findPeriod(long id, LocalDateTime start, LocalDateTime end) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        List<Report> reportList = getReportOnOrg(organization, start, end);
        List<Device> deviceList = getAllDevice(organization);
        return findReportPeriod(deviceList, reportList, start, end);
    }

    private List<Device> getAllDevice(Organization org) {
        List<Device> deviceList = new ArrayList<>();
        if (org != null) {
            for (Building building : org.getBuildings()) {
                for (Parking parking : building.getParking()) {
                    deviceList.addAll(find(parking.getZoneNumber()));
                }
            }
        }
        return deviceList;
    }

    private ReportMomentDto createReportMoment(List<Device> list) {
        ReportMomentDto reportMomentDto = new ReportMomentDto();
        reportMomentDto.setTotal(list.size());
        reportMomentDto.setStatusTaken((int) list.stream().filter(device -> device.getMessages().get(0).getStatus() == 1).count());
        reportMomentDto.setStatusFree((int) list.stream().filter(device -> device.getMessages().get(0).getStatus() == 0).count());
        reportMomentDto.setPercent((double) Math.floorDiv(reportMomentDto.getStatusTaken() * 100, reportMomentDto.getTotal()));
        return reportMomentDto;
    }

    private List<Device> find(int zoneId) {
        return deviceRepository.findAll(zoneId).stream()
                .map(device -> {
                    device.getMessages().subList(0, device.getMessages().size() - 1).clear();
                    return device;
                })
                .collect(Collectors.toList());
    }

    private List<Report> getReportOnOrg(Organization organization, LocalDateTime start, LocalDateTime end) {
        if (organization != null) {
            return reportRepository.getReport(organization, start, end);
        }
        return null;
    }

    private ReportPeriodDto findReportPeriod(List<Device> listD, List<Report> listR, LocalDateTime start, LocalDateTime end) {
        boolean flag = false;
        List<Integer> countingTime = new ArrayList<>();
        LocalDateTime intermediateTime = null;
        int minute = 0;
        Map<Device, List<Report>> result = new HashMap<>();
        for (Report r : listR) {
            Device d = r.getDevice();
            result.compute(d, (k, v) -> (v == null) ? new ArrayList<>(Arrays.asList(r)) : plus(v, r));
        }
        for (Map.Entry<Device, List<Report>> entry : result.entrySet()) {
            flag = true;
            for (Report r : entry.getValue()) {
                if (flag) {
                    flag = false;
                    if (r.getStatus() == 0) {
                        minute = start.getMinute() + r.getTimeMessage().getMinute();
                        continue;
                    }
                }
                if (r.getStatus() == 1) {
                    intermediateTime = r.getTimeMessage();
                } else {
                    minute += intermediateTime.getMinute() + r.getTimeMessage().getMinute();
                    start = null;
                }
                countingTime.add(minute);
            }
            if (start != null) {
                minute += end.getMinute() - intermediateTime.getMinute();
                intermediateTime = null;
            }
        }
        ReportPeriodDto reportPeriodDto = new ReportPeriodDto();
        reportPeriodDto.setTotal(listD.size());
        reportPeriodDto.setPercent((double) Math.floorDiv(reportPeriodDto.getTotal() * 60 * 100, minute));
        return reportPeriodDto;
    }

    private List<Report> plus(List<Report> list, Report report) {
        list.add(report);
        return list;
    }
}
