package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.DrDumpster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrDumpsterDao extends CrudRepository<DrDumpster, Integer> {
    List<DrDumpster> findByDailyReportIdAndDumpstersStatus(Integer dailyReportId, String status);
}
