package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.wpz.entity.Organization;
import ru.wpz.entity.Report;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

    @Query("select r from Report  r where r.organizationId = org and r.timeMessage >= start and r.timeMessage <= end")
    List<Report> getReport(@Param("org") Organization org, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
