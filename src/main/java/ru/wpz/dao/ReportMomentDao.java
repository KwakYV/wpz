package ru.wpz.dao;

import ru.wpz.entity.ReportMoment;

public interface ReportMomentDao {
    public Iterable<ReportMoment> findAll(int zoneId);
}
