package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.Dumpster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DumpsterDao extends CrudRepository<Dumpster, Integer> {
    Dumpster findByDailyReportIdAndStatus(Integer dailyReportId, String status);

    List<Dumpster> findByDailyReportIdIn(List<Integer> dailyReportIds);
}
