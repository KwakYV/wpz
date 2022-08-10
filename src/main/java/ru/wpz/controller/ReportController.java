package ru.wpz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.DeviceBusyDto;
import ru.wpz.dto.ReportMomentDto;
import ru.wpz.service.ReportService;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/report")
@AllArgsConstructor
@Api(value = "report", produces = "Контроллер для получения отчетов")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/moment/building/{buildingId}")
    @ApiOperation("Получение отчетов занятых парковочных мест по объектам в данный момент")
    public ReportMomentDto getReportAtMoment(@PathVariable Long buildingId) {
        return reportService.calcDeviceBusyAtMoment(buildingId);
    }


    @PostMapping("/day")
    @ApiOperation("Получение отчетов занятых парковочных мест по объекту в указанный день")
    public List<DeviceBusyDto> getReportByDay(@RequestBody Long buildingId, @RequestBody LocalDateTime day) {
        return reportService.calcDeviceBusyByDay(buildingId, day);
    }

    @PostMapping("/period")
    @ApiOperation("Получение отчетов занятых парковочных мест по объекту в указанный период времени")
    public List<DeviceBusyDto> getReportByPeriod(@RequestBody Long id, @RequestBody LocalDateTime start, @RequestBody LocalDateTime end) {
        return reportService.calcDeviceBusyByPeriod(start, end, id);
    }
}
