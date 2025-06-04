package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.Tool;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToolDao extends CrudRepository<Tool, Integer> {
    List<Tool> findByDailyReportIdAndStatus(Integer dailyReportId, String status);

    List<Tool> findByDailyReportIdIn(List<Integer> dailyReportIds);
}
