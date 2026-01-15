package com.ck.wi.service.dailyReport;

import java.util.List;

import com.ck.wi.model.dto.dailyReport.creation.DrToolCreateDto;
import com.ck.wi.model.entity.dailyReport.Tool;

public interface ITool {
    List<Tool> findByDailyReportId(Integer dailyReportId);

    List<Tool> findByDailyReportIds(List<Integer> dailyReportIds);

    List<DrToolCreateDto> getLastReportTools(String jobNum);
}
