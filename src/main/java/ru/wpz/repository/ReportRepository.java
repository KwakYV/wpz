package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.wpz.entity.Report;


import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

}
