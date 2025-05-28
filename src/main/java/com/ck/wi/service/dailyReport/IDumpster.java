package com.ck.wi.service.dailyReport;

import com.ck.wi.model.entity.dailyReport.Dumpster;

import java.util.List;

public interface IDumpster {
    List<Dumpster> findByDailyReportId(Integer id);
}
