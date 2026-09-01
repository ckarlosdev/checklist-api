package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolDao extends JpaRepository<Tool, Integer> {
    List<Tool> findByDailyReportIdAndStatus(Integer dailyReportId, String status);

    List<Tool> findByDailyReportIdIn(List<Integer> dailyReportIds);

    @Query(value = "SELECT t.* " +
            "FROM tools t " +
            "WHERE t.status='1' " +
            "AND t.daily_report_id = ( " +
            "    SELECT dr.daily_report_id " +
            "    FROM daily_reports dr " +
            "    INNER JOIN  tools t on t.daily_report_id = dr.daily_report_id " +
            "    WHERE dr.number = :jobNumber " +
            "    AND dr.status='1' AND t.status='1' " +
            "    ORDER BY dr.date DESC, dr.daily_report_id DESC " +
            "    LIMIT 1 " +
            ");", nativeQuery = true)
    List<Tool> findToolsFromLastReport(@Param("jobNumber") String jobNumber);

}
