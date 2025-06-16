package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.Tool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolDao extends CrudRepository<Tool, Integer> {
    List<Tool> findByDailyReportIdAndStatus(Integer dailyReportId, String status);

    List<Tool> findByDailyReportIdIn(List<Integer> dailyReportIds);
}
