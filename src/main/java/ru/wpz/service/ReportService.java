package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wpz.dto.DeviceBusyDto;
import ru.wpz.dto.ReportMomentDto;
import ru.wpz.repository.DeviceRepository;

import java.time.LocalDateTime;
import java.util.*;


@Service
@AllArgsConstructor
public class ReportService {

    private final DeviceRepository deviceRepository;

    /**
     *
     * @param buildingId - уникальный идентификатор объекта
     * @return - Возвращает ReportMomentDto
     */
    public ReportMomentDto calcDeviceBusyAtMoment(Long buildingId) {
        return deviceRepository.findBusyReportAtMoment(buildingId);
    }

    /**
     *
     * @param buildingId - уникальный идентификатор объекта
     * @param dateTime - дата, за которую нужно выполнить расчет загрузки парковочных мест
     * @return - Возвращает список объетов DeviceBusyDto
     */
    public List<DeviceBusyDto> calcDeviceBusyByDay(Long buildingId, LocalDateTime dateTime) {
        return deviceRepository.findDeviceBusyTimeByDay(dateTime, buildingId);
    }

    /**
     *
     * @param start - начало периода
     * @param end - конец периода
     * @param buildingId - уникальный идетификатор объекта
     * @return - Возвращает список объетов DeviceBusyDto
     */
    public List<DeviceBusyDto> calcDeviceBusyByPeriod(LocalDateTime start, LocalDateTime end, Long buildingId) {
        return deviceRepository.findDeviceBusyTimeByPeriod(start, end, buildingId);
    }



}
