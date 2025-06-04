package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.DrEmployee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrEmployeeDao extends CrudRepository<DrEmployee, Integer> {
    List<DrEmployee> findByDailyReportIdAndStatus(Integer dailyReportId, String status);

    List<DrEmployee> findByDailyReportIdIn(List<Integer> dailyReportIds);
}
