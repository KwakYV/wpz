package ru.wpz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.DayReportDto;
import ru.wpz.dto.ReportMomentDto;
import ru.wpz.dto.ReportPeriodDto;
import ru.wpz.service.ReportService;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/v1/report")
@AllArgsConstructor
@Api(value = "report", produces = "Контроллер для получения отчетов")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/organization/{id}")
    @ApiOperation("Получение отчетов занятых парковочных мест по организациям в данный момент")
    public ReportMomentDto showDevOrg(@PathVariable long id){
        return reportService.findDevOrg(id);
    }


    @PostMapping("/organization/day")
    @ApiOperation("Получение отчетов занятых парковочных мест по организациям в указанный день")
    public DayReportDto dayDevOrg(@RequestBody long id, @RequestBody LocalDateTime day){
        return reportService.findDayDevOrg(id, day);
    }

    @PostMapping("/organization/period")
    @ApiOperation("Получение отчетов занятых парковочных мест по организациям в указанный период времени")
    public ReportPeriodDto periodDevOrg(@RequestBody long id, @RequestBody LocalDateTime start, @RequestBody LocalDateTime end){
        return reportService.findPeriod(id, start, end);
    }


}
