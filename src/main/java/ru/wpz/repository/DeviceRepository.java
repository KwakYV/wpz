package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.wpz.entity.Device;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query("FROM Device d WHERE d.zoneId = zoneId")
    List<Device> findAll(@Param("zoneId") int zoneId);
}
