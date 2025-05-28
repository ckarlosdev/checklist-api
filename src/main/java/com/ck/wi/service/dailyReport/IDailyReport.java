package com.ck.wi.service.dailyReport;

import com.ck.wi.model.entity.dailyReport.DailyReport;

import java.util.List;

public interface IDailyReport {
    List<DailyReport> findByNumber(String number);

    DailyReport findById(Integer id);

    List<DailyReport> findAll();
}
