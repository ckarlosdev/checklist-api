package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.DrDumpster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrDumpsterDao extends JpaRepository<DrDumpster, Integer> {
    List<DrDumpster> findByDailyReportIdAndDumpstersStatus(Integer dailyReportId, String status);
}
