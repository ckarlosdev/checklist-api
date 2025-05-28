package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.Dumpster;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DumpsterDao extends CrudRepository<Dumpster, Integer> {
    List<Dumpster> findByDailyReportIdAndStatus(Integer dailyReportId, String status);
}
