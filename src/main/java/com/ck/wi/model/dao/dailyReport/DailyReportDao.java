package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.DailyReport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DailyReportDao extends CrudRepository<DailyReport, Integer> {
    List<DailyReport> findByNumberAndStatus(String number, String status);

    List<DailyReport> findByNumberIn(List<String> number);
}
