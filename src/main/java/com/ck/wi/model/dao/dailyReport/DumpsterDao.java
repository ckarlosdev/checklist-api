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
            value = "select CAST(SUM(concret40) + SUM(concret35) + SUM(concret30) + SUM(concret20) + SUM(concret12) + SUM(concretquad) + SUM(concretsemi) + SUM(concretgondola) AS SIGNED) as 'concrete', " +
                    "    CAST(SUM(metal40) + SUM(metal35) + SUM(metal30) + SUM(metal20) + SUM(metal12) + SUM(metalquad) + SUM(metalsemi) + SUM(metalgondola) AS SIGNED) as 'metal' , " +
                    "    CAST(SUM(cd40) + SUM(cd35) + SUM(cd30) + SUM(cd20) + SUM(cd12) +  SUM(cdquad) + SUM(cdsemi) + SUM(cdgondola) AS SIGNED) as 'cd' " +
                    "from daily_reports DR " +
                    "inner join dumpsters D on DR.daily_report_id=D.daily_report_id " +
                    "where DR.number = :jobNumber ",
            nativeQuery = true
    )
    DumpsterSummaryDto findSummaryByJobNumber(@Param("jobNumber") String jobNumber);
}
