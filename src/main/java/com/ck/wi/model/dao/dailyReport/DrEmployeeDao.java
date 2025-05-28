package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.DrEmployee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DrEmployeeDao extends CrudRepository<DrEmployee, Integer> {
    List<DrEmployee> findByDailyReportIdAndStatus(Integer dailyReportId, String status);
}
