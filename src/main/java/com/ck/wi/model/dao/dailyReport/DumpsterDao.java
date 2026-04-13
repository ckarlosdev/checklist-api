package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.dto.dailyReport.DailyReportSummaryDto;
import com.ck.wi.model.dto.dailyReport.DumpsterSummaryDto;
import com.ck.wi.model.entity.dailyReport.Dumpster;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DumpsterDao extends CrudRepository<Dumpster, Integer> {
    Dumpster findByDailyReportIdAndStatus(Integer dailyReportId, String status);

    List<Dumpster> findByDailyReportIdIn(List<Integer> dailyReportIds);

    @Query(
            value = "CALL sp_get_dumpster_totals(:jobNumber)",
            nativeQuery = true
    )
    DumpsterSummaryDto findSummaryByJobNumber(@Param("jobNumber") String jobNumber);
}
