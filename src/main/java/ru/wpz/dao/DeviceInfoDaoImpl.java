package ru.wpz.dao;


import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.wpz.entity.DeviceStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Component
public class DeviceInfoDaoImpl implements DeviceInfoDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<DeviceStatus> getDevicesWithLastStatus(Long zoneId) {
        String sql = "select t.id, t.dev_number, t.status, t.x_coord, t.y_coord \n" +
                "from( \n" +
                "select d.id, d.dev_number, max(m.created_dt) over (partition by d.id) max_dt, m.status, m.created_dt, d.x_coord, d.y_coord \n" +
                "from wpz.device d \n" +
                "left join wpz.message m on d.id = m.dev_id \n" +
                "where d.zone_id = :zoneId) t \n" +
                "where t.created_dt = t.max_dt or t.max_dt is null";
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("zoneId", zoneId);

        return namedParameterJdbcTemplate.query(sql, parameters,
                new ResultSetExtractor<List<DeviceStatus>>() {
                    @Override
                    public List<DeviceStatus> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        List<DeviceStatus> resultList = new ArrayList<>();
                        DeviceStatus deviceStatus = null;
                        while (rs.next()) {
                            deviceStatus = DeviceStatus.builder()
                                    .id(rs.getLong("id"))
                                    .status(rs.getInt("status"))
                                    .deviceNumber(rs.getLong("dev_number"))
                                    .xCor(rs.getInt("x_coord"))
                                    .yCor(rs.getInt("y_coord"))
                                    .build();
                            resultList.add(deviceStatus);
                        }
                        return resultList;
                    }
                });
    }
}
