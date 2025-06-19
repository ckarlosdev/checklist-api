package com.ck.wi.service.dailyReport;

import com.ck.wi.model.dto.dailyReport.DumpsterSummaryDto;
import com.ck.wi.model.entity.dailyReport.Dumpster;

import java.util.List;

public interface IDumpster {
    Dumpster findByDailyReportId(Integer dailyReportId);

    List<Dumpster> findByDailyReportIds(List<Integer> dailyReportIds);

    DumpsterSummaryDto findSummaryByJobNumber(String jobNumber);
}
