package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wpz.dto.DayReportDto;
import ru.wpz.dto.ReportPeriodDto;
import ru.wpz.entity.Building;
import ru.wpz.entity.Organization;
import ru.wpz.entity.Parking;
import ru.wpz.entity.ReportMoment;
import ru.wpz.entity.DeviceBusyTime;
import ru.wpz.entity.DeviceNumber;
import ru.wpz.repository.OrganizationRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class ReportService {

    @PersistenceContext
    private EntityManager em;

    private final OrganizationRepository organizationRepository;

    // получаем отчет о занатых и свободных парковочных местах в данный момент
    public ReportMoment findDevOrg(long id) {
        return findReportMoment(id).get(0);
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
                deviceBusyTimes.addAll(findDeviceBusyTime(devNum.getDevNumber(), start.plusHours(i), end.plusHours(i + 1)));
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
            deviceBusyTimes.addAll(findDeviceBusyTime(devNum.getDevNumber(), start, end));
        }
        return findReportPeriod(deviceBusyTimes, deviceNumbers.size());
    }

    /*
    для метода получаешего отчет о занатых и свободных парковочных местах в данный момент
    получаем список всех датчиков и их актуальные статусы
     */
    private ReportMoment getListReportMoment(long id) {
        List<ReportMoment> reportList = findReportMoment(id);
        return reportList.get(0);
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
                    reportList.addAll(findDeviceNumber(parking.getZoneNumber()));
                }
            }
        }
        return reportList;
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

    private List<ReportMoment> findReportMoment(long id){
        final String REPORT_MOMENT = "with res as(" +
                "select sum(tab.cnt_devices) as total," +
                "cast(split_part(string_agg(cast(tab.cnt_devices as varchar), ':'), ':', 1) as int) as free_count," +
                "cast(split_part(string_agg(cast(tab.cnt_devices as varchar), ':'), ':', 2) as int) as busy_count" +
                "from (select count(t.dev_number) cnt_devices, coalesce(t.status, 0) as status" +
                "from (select d.id, d.dev_number, max(m.created_dt) over (partition by d.id) max_dt, m.status, m.created_dt" +
                "from device d" +
                "join parking p on p.id = d.zone_id" +
                "join building b on b.id = p.obj_id" +
                "left join message m on d.id = m.dev_id" +
                "where b.id = \" + id + \" ) t where t.created_dt = t.max_dt or t.max_dt is null" +
                "group by t.status" +
                "order by t.status) tab)" +
                "select r.total," +
                "r.busy_count," +
                "r.free_count," +
                "cast(r.busy_count * 100 as float)/  cast(r.total as float) as percent" +
                "from res r";
        return em.createNativeQuery(REPORT_MOMENT, ReportMoment.class).getResultList();
    }

    private List<DeviceBusyTime> findDeviceBusyTime(int device, LocalDateTime start, LocalDateTime end){
        final String DEVICE_BUSY_TIME = "with total as(with result as(" +
                "SELECT 0 as id, " + device + " as device, 2 as status, DATE_TRUNC('second', TIMESTAMP " + start + " ) as timeMessage" +
                "union all " +
                "select r.id, r.device, r.status, r.timeMessage from report r where r.device = " + device +
                " and r.timeMessage > " + start + " and r.timeMessage > " + end + " " +
                "union all" +
                "SELECT  0 as id, " + device + " as device, 2 as status, DATE_TRUNC('second', TIMESTAMP " + end + " ) as timeMessage)" +
                "select r1.device, r1.timeMessage, r2.timeMessage," +
                "    (DATE_PART('day', r2.timeMessage - r1.timeMessage)*24 +" +
                "    DATE_PART('hours', r2.timeMessage - r1.timeMessage)*60 +" +
                "    DATE_PART('minute', r2.timeMessage - r1.timeMessage)) as busy" +
                "    from result r1" +
                "inner join result r2 on r2.id = (select r.id from result r where r1.timeMessage < r.timeMessage order by r.timeMessage limit 1) " +
                "where r1.status <> 0 and r2.status <> 1 and r1.status <> r2.status)" +
                "select t.device, sum(t.busy) from total t where t.busy > 0 group by t.device ";
        return em.createNativeQuery(DEVICE_BUSY_TIME, DeviceBusyTime.class).getResultList();
    }

    private List<DeviceNumber> findDeviceNumber(int zoneId){
        final String DEVICE_BUSY_TIME = "select d.dev_number from device d where d.zone_id = " + zoneId;
        return em.createNativeQuery(DEVICE_BUSY_TIME, DeviceNumber.class).getResultList();
    }
}
