package ru.wpz.dao;

import org.springframework.stereotype.Repository;
import ru.wpz.entity.ReportMoment;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@Repository
public class JdbcReportMomentDaoImpl implements ReportMomentDao{

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:2345/wpz", "wpz", "wpz");
    }

    @Override
    public Set<ReportMoment> findAll(int zoneId) {
        Set<ReportMoment> result = new HashSet<>();
        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("select t.dev_number, t.max_dt, t.status " +
                    "from(select d.dev_number, max(m.created_dt) over (partition by d.id) max_dt," +
                    " m.status, m.created_dt from device d join message m on d.id = m.dev_id where d.zone_id ="
                    + zoneId +") t where t.created_dt = t.max_dt");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                final ReportMoment reportMoment = ReportMoment.builder()
                        .devNumber(resultSet.getInt("dev_number"))
                        .maxDt(resultSet.getTimestamp("max_dt").toLocalDateTime())
                        .status(resultSet.getInt("status"))
                        .build();
                result.add(reportMoment);
            }
            preparedStatement.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
