package ru.wpz.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.wpz.model.DeviceBusyTime;
import ru.wpz.model.DeviceNumber;
import ru.wpz.model.ReportMoment;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;


@Component
@AllArgsConstructor
public class ReportDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReportMoment> REPORT_MOMENT = (ResultSet resultSet, int rowNun) ->
            ReportMoment.builder().devNumber(resultSet.getInt("dev_number"))
            .maxDt(resultSet.getTimestamp("max_dt").toLocalDateTime())
            .status(resultSet.getInt("status"))
            .build();

    private final RowMapper<DeviceBusyTime> DEVICE_BUSY_TIME = (ResultSet resultSet, int rowNun) ->
            DeviceBusyTime.builder().devId(resultSet.getInt("device"))
                    .busy(resultSet.getInt("sum"))
                    .build();

    private final RowMapper<DeviceNumber> DEVICE_NUMBER = (ResultSet resultSet, int rowNun) ->
            DeviceNumber.builder().devNumber(resultSet.getInt("device"))
                    .build();

    public List<ReportMoment> findReportMoment(int zoneId){
        return jdbcTemplate.query("select t.dev_number, t.max_dt, t.status " +
                "from(select d.dev_number, max(m.created_dt) over (partition by d.id) max_dt," +
                " m.status, m.created_dt from device d join message m on d.id = m.dev_id where d.zone_id ="
                + zoneId + ") t where t.created_dt = t.max_dt", REPORT_MOMENT);
    }

    public List<DeviceBusyTime> findDeviceBusyTime(int device, LocalDateTime start, LocalDateTime end){
        return jdbcTemplate.query("with total as(with result as(" +
                "SELECT 0 as id, " + device + " as device, 2 as status, DATE_TRUNC('second', TIMESTAMP " + start + " ) as timeMessage" +
                "union all " +
                "select r.id, r.device, r.status, r.timeMessage from report r where r.device = " + device +
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
