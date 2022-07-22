package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.wpz.entity.Device;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query("select d FROM Device d WHERE d.zoneId =: zoneId")
    List<Device> findAll(@Param("zoneId") int zoneId);

//    select *
//    from(
//            select d.dev_number, max(m.created_dt) over (partition by d.id) max_dt, m.status, m.created_dt
//    from wpz.device d
//    join wpz.message m on d.id = m.dev_id
//    where d.zone_id = 1) t
//    where t.created_dt = t.max_dt;

}
