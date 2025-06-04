package com.ck.wi.service.dailyReport;

import com.ck.wi.model.entity.dailyReport.DrEmployee;

import java.util.List;

public interface IDrEmployee {
    List<DrEmployee> findByDailyReportId(Integer dailyReportId);

    List<DrEmployee> findByDailyReportIds(List<Integer> dailyReportId);
}
