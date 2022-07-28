package ru.wpz.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.wpz.dto.ReportMomentDto;
import ru.wpz.model.DeviceBusyTime;
import ru.wpz.model.DeviceNumber;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ReportDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReportMomentDto> REPORT_MOMENT_DTO = (ResultSet resultSet, int rowNun) ->
            ReportMomentDto.builder().total(resultSet.getInt("total"))
                    .statusTaken(resultSet.getInt("busy_count"))
                    .statusFree(resultSet.getInt("free_count"))
                    .percent(resultSet.getFloat("percent"))
            .build();

    private final RowMapper<DeviceBusyTime> DEVICE_BUSY_TIME = (ResultSet resultSet, int rowNun) ->
            DeviceBusyTime.builder().devId(resultSet.getInt("device"))
                    .busy(resultSet.getInt("sum"))
                    .build();

    private final RowMapper<DeviceNumber> DEVICE_NUMBER = (ResultSet resultSet, int rowNun) ->
            DeviceNumber.builder().devNumber(resultSet.getInt("device"))
                    .build();

    public List<ReportMomentDto> findReportMoment(long id){
        return jdbcTemplate.query("with res as(" +
                "select sum(tab.cnt_devices) as total," +
                "cast(split_part(string_agg(cast(tab.cnt_devices as varchar), ':'), ':', 1) as int) as free_count," +
                "cast(split_part(string_agg(cast(tab.cnt_devices as varchar), ':'), ':', 2) as int) as busy_count" +
                "from (select count(t.dev_number) cnt_devices, coalesce(t.status, 0) as status" +
                "from (select d.id, d.dev_number, max(m.created_dt) over (partition by d.id) max_dt, m.status, m.created_dt" +
                "from device d" +
                "join parking p on p.id = d.zone_id" +
                "join building b on b.id = p.obj_id" +
                "left join message m on d.id = m.dev_id" +
                "where b.id = " + id + " ) t where t.created_dt = t.max_dt or t.max_dt is null" +
                "group by t.status" +
                "order by t.status) tab)" +
                "select r.total," +
                "r.busy_count," +
                "r.free_count," +
                "cast(r.busy_count * 100 as float)/  cast(r.total as float) as percent" +
                "from res r;", REPORT_MOMENT_DTO);
    }

    public List<DeviceBusyTime> findDeviceBusyTime(int device, LocalDateTime start, LocalDateTime end){
        return jdbcTemplate.query("with total as(with result as(" +
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
                "select t.device, sum(t.busy) from total t where t.busy > 0 group by t.device ", DEVICE_BUSY_TIME);
    }

    public List<DeviceNumber> findDeviceNumber(int zoneId){
        return jdbcTemplate.query("select d.dev_number from device d where d.zone_id = " + zoneId, DEVICE_NUMBER);
    }
}
