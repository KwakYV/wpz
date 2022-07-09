package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wpz.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
