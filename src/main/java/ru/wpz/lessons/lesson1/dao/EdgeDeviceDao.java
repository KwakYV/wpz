package ru.wpz.lessons.lesson1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wpz.lessons.lesson1.entity.EdgeDevice;

@Repository
public interface EdgeDeviceDao extends JpaRepository<EdgeDevice, Long> {
}
