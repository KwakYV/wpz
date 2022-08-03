package ru.wpz.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import ru.wpz.dto.DeviceBusyDto;
import ru.wpz.dto.DeviceStatusDto;
import ru.wpz.dto.ReportMomentDto;

import javax.persistence.*;
import java.util.List;
@NamedNativeQueries({@NamedNativeQuery(name = "Device.findDevicesWithLastStatus",
        query = "select t.id, t.dev_number, t.status, t.x_coord, t.y_coord \n" +
                "from( \n" +
                "select d.id, d.dev_number, max(m.created_dt) over (partition by d.id) max_dt, m.status, m.created_dt, d.x_coord, d.y_coord \n" +
                "from wpz.device d \n" +
                "left join wpz.message m on d.id = m.dev_id \n" +
                "where d.zone_id = :zoneId) t \n" +
                "where t.created_dt = t.max_dt or t.max_dt is null",
        resultSetMapping = "Mapping.DeviceStatusDto"),
        @NamedNativeQuery(name="Device.findBusyReportAtMoment",
        query="with res as(\n" +
                "    select sum(tab.cnt_devices) as total,\n" +
                "           cast(split_part(string_agg(cast(tab.cnt_devices as varchar), ':'), ':', 1) as int) as free_count,\n" +
                "           cast(split_part(string_agg(cast(tab.cnt_devices as varchar), ':'), ':', 2) as int) as busy_count\n" +
                "\n" +
                "    from (\n" +
                "             select count(t.dev_number) cnt_devices, coalesce(t.status, 0) as status\n" +
                "             from (\n" +
                "                      select d.id, d.dev_number, max(m.created_dt) over (partition by d.id) max_dt, m.status, m.created_dt\n" +
                "                      from wpz.device d\n" +
                "                               join wpz.parking p on p.id = d.zone_id\n" +
                "                               join wpz.building b on b.id = p.obj_id\n" +
                "                               left join wpz.message m on d.id = m.dev_id\n" +
                "                      where b.id = :buildingId\n" +
                "                        ) t\n" +
                "             where t.created_dt = t.max_dt\n" +
                "                or t.max_dt is null\n" +
                "             group by t.status\n" +
                "             order by t.status\n" +
                "         ) tab\n" +
                ")\n" +
                "select r.total,\n" +
                "       r.busy_count,\n" +
                "       r.free_count,\n" +
                "       cast(r.busy_count * 100 as float)/  cast(r.total as float) as percent\n" +
                "from res r",
        resultSetMapping = "Mapping.ReportMomentDto"),
        @NamedNativeQuery(
                name="Device.findDeviceBusyTimeByPeriod",
                query="with result as (\n" +
                        "    select t2.device_id, t2.status, sum(t2.diff) as sum\n" +
                        "    from (\n" +
                        "             select t1.*,\n" +
                        "                    (coalesce(t1.next_sec, extract(epoch from (:end))) - t1.sec) as diff \n" +
                        "             from (\n" +
                        "                      select t.*, lead(t.sec,1) over (partition by t.device_id order by t.message_dt)as next_sec\n" +
                        "                      from (\n" +
                        "                               select m.*, extract(epoch from (m.message_dt)) as sec\n" +
                        "                               from wpz.report m\n" +
                        "                                where m.message_dt between :start and :end\n" +
                        "                                       and m.building_id = :buildingId\n" +
                        "                               order by m.message_dt\n" +
                        "                           ) t\n" +
                        "                  ) t1\n" +
                        "\n" +
                        "         ) t2\n" +
                        "    group by t2.device_id, t2.status\n" +
                        "    order by t2.status\n" +
                        ")\n" +
                        "select r1.device_id,  r2.sum*100/(r1.sum + r2.sum) as busy_percent\n" +
                        "from result r1\n" +
                        "join result r2 on r2.device_id = r1.device_id and r2.status != r1.status\n" +
                        "where r1.status = 0",
                resultSetMapping = "Mapping.DeviceBusyDto"
        ),
        @NamedNativeQuery(
                name="Device.findDeviceBusyTimeByDay",
                query="with result as (\n" +
                        "select t3.device_id, t3.status, sum(t3.diff) as sum\n" +
                        "from (\n" +
                        "         select t2.device_id, t2.status, (coalesce(t2.next_seconds, extract(epoch from (:day::date + interval '1 day'))) - t2.seconds) as diff\n" +
                        "from (\n" +
                        "         select t1.device_id, t1.status,  t1.seconds, lead(t1.seconds, 1) over (partition by t1.device_id order by t1.message_dt) as next_seconds\n" +
                        "         from (\n" +
                        "                  select r.device_id, r.status, r.message_dt, extract(epoch from (r.message_dt)) as seconds\n" +
                        "                  from wpz.report r\n" +
                        "                  where r.message_dt::date = :day::date\n" +
                        "                    and r.building_id = :buildingId\n" +
                        "\n" +
                        "              ) t1\n" +
                        "\n" +
                        "         ) t2\n" +
                        ") t3\n" +
                        "group by t3.device_id, t3.status)\n" +
                        "select r1.device_id, r2.sum*100/(r1.sum + r2.sum) as busy_percent\n" +
                        "from result r1\n" +
                        "join result r2 on r2.device_id = r1.device_id and r2.status != r1.status\n" +
                        "where r1.status = 0",
                resultSetMapping = "Mapping.DeviceBusyDto"
        )
})
@SqlResultSetMappings({
        @SqlResultSetMapping(name="Mapping.DeviceStatusDto", classes = @ConstructorResult(targetClass = DeviceStatusDto.class,
                columns = {
                        @ColumnResult(name="id", type = Long.class),
                        @ColumnResult(name="dev_number", type = Integer.class),
                        @ColumnResult(name="status", type = Integer.class),
                        @ColumnResult(name="x_coord", type = Integer.class),
                        @ColumnResult(name="y_coord", type = Integer.class)
                })),
        @SqlResultSetMapping(name="Mapping.ReportMomentDto", classes = @ConstructorResult(targetClass = ReportMomentDto.class,
                columns = {
                        @ColumnResult(name="total", type = Integer.class),
                        @ColumnResult(name="busy_count", type = Integer.class),
                        @ColumnResult(name="free_count", type = Integer.class),
                        @ColumnResult(name="percent", type = Float.class)
                })),
        @SqlResultSetMapping(name="Mapping.DeviceBusyDto", classes = @ConstructorResult(targetClass = DeviceBusyDto.class,
                columns = {
                        @ColumnResult(name="device_id", type = Long.class),
                        @ColumnResult(name="busy_percent", type = Float.class)
                }))
})

@Entity
@Table(schema = "wpz",name="device")
@Data
@ApiModel
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dev_number")
    private Integer devNumber;

    @Column(name = "zone_id")
    private Long zoneId;

    @Column(name="x_coord")
    private int xCor;

    @Column(name="y_coord")
    private int yCor;


}
