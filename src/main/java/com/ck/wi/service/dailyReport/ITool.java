package com.ck.wi.service.dailyReport;

import java.util.List;
import com.ck.wi.model.entity.dailyReport.Tool;

public interface ITool {
    List<Tool> findByDailyReportId(Integer id);
}
