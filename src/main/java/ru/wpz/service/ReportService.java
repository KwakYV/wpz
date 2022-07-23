package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wpz.dao.ReportDao;
import ru.wpz.dto.DayReportDto;
import ru.wpz.dto.ReportMomentDto;
import ru.wpz.dto.ReportPeriodDto;
import ru.wpz.entity.*;
import ru.wpz.model.DeviceBusyTime;
import ru.wpz.model.DeviceNumber;
import ru.wpz.model.ReportMoment;
import ru.wpz.repository.DeviceRepository;
import ru.wpz.repository.OrganizationRepository;
import ru.wpz.repository.ReportRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;


@Service
@AllArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final OrganizationRepository organizationRepository;
    private final ReportDao reportDao;

    // после получения нового message создаем отчет
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

    // получаем отчет о занатых и свободных парковочных местах в данный момент
    public ReportMomentDto findDevOrg(long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        List<ReportMoment> momentSet = getListDevice(organization);
        return createReportMoment(momentSet);
    }

    // получаем отчет о занатых и свободных парковочных местах в указанный день и формируем DTO для фронта
    public List<DayReportDto> findDayDevOrg(long id, LocalDateTime day) {
        LocalDateTime start = day.with(ChronoField.NANO_OF_DAY, LocalTime.MIN.toNanoOfDay());
        LocalDateTime end = day.with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay());
        Organization organization = organizationRepository.findById(id).orElse(null);
        List<DeviceNumber> deviceNumbers = getListDeviceOnOrg(organization);
        List<DeviceBusyTime> deviceBusyTimes = new ArrayList<>();
        List<DayReportDto> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            for (DeviceNumber devNum : deviceNumbers) {
                deviceBusyTimes.addAll(reportDao.findDeviceBusyTime(devNum.getDevNumber(), start.plusHours(i), end.plusHours(i + 1)));
            }
            DayReportDto dayReportDto = new DayReportDto();
            dayReportDto.setNumber(i);
            dayReportDto.setNumber(deviceNumbers.size());
            dayReportDto.setPercent(findReportPeriod(deviceBusyTimes, deviceNumbers.size()).getPercent());
        }
        return list;
    }

    // получаем отчет о занатых и свободных парковочных местах в указанный период времени и формируем DTO для фронта
    public ReportPeriodDto findPeriod(long id, LocalDateTime start, LocalDateTime end) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        List<DeviceNumber> deviceNumbers = getListDeviceOnOrg(organization);
        List<DeviceBusyTime> deviceBusyTimes = new ArrayList<>();
        for (DeviceNumber devNum : deviceNumbers) {
            deviceBusyTimes.addAll(reportDao.findDeviceBusyTime(devNum.getDevNumber(), start, end));
        }
        return findReportPeriod(deviceBusyTimes, deviceNumbers.size());
    }

    /*
    для метода получаешего отчет о занатых и свободных парковочных местах в данный момент
    получаем список всех датчиков и их актуальные статусы
     */
    private List<ReportMoment> getListDevice(Organization org) {
        List<ReportMoment> reportList = new ArrayList<>();
        if (org != null) {
            for (Building building : org.getBuildings()) {
                for (Parking parking : building.getParking()) {
                    reportList.addAll(reportDao.findReportMoment(parking.getZoneNumber()));
                }
            }
        }
        return reportList;
    }

    /*
    для метода получаещего отчет о занатых и свободных парковочных местах в данный момент
    и для метода получаещего отчет о занатых и свободных парковочных местах в указанный период времени
    получаем список всех датчики
     */
    private List<DeviceNumber> getListDeviceOnOrg(Organization org) {
        List<DeviceNumber> reportList = new ArrayList<>();
        if (org != null) {
            for (Building building : org.getBuildings()) {
                for (Parking parking : building.getParking()) {
                    reportList.addAll(reportDao.findDeviceNumber(parking.getZoneNumber()));
                }
            }
        }
        return reportList;
    }

    /*
    для метода получаещего отчет о занатых и свободных парковочных местах в данный момент
    подсчитываем общее кол-во датчиков, кол-во занатых и свободных парковок, процент заполнение парковки
    формируем DTO для фронта
     */
    private ReportMomentDto createReportMoment(List<ReportMoment> list) {
        ReportMomentDto reportMomentDto = new ReportMomentDto();
        reportMomentDto.setTotal(list.size());
        reportMomentDto.setStatusTaken((int) list.stream().filter(report -> report.getStatus() == 1).count());
        reportMomentDto.setStatusFree((int) list.stream().filter(report -> report.getStatus() == 0).count());
        reportMomentDto.setPercent((double) Math.floorDiv(reportMomentDto.getStatusTaken() * 100, reportMomentDto.getTotal()));
        return reportMomentDto;
    }

    private ReportPeriodDto findReportPeriod(List<DeviceBusyTime> deviceBusyTimes, int totalDevice){
        int totalTime = 0;
        for (DeviceBusyTime device : deviceBusyTimes) {
            totalTime += device.getBusy();
        }
        ReportPeriodDto reportPeriodDto = new ReportPeriodDto();
        reportPeriodDto.setTotal(totalDevice);
        reportPeriodDto.setPercent((double) Math.floorDiv(totalTime * 60 * 100, totalDevice));
        return reportPeriodDto;
    }

}
