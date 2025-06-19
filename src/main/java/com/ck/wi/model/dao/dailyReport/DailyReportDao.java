package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.dto.dailyReport.DailyReportSummaryDto;
import com.ck.wi.model.entity.dailyReport.DailyReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyReportDao extends CrudRepository<DailyReport, Integer> {
    List<DailyReport> findByNumberAndStatus(String number, String status);

    List<DailyReport> findByNumberIn(List<String> number);

//    DailyReport findByNumberAndDate(String number, Date date);

    @Query(
            value = "SELECT * FROM daily_reports WHERE number = :number AND DATE(date) = :date",
            nativeQuery = true
    )
    DailyReport findByNumberAndDate(@Param("number") String number, @Param("date") LocalDate date);

    @Query(
            value = "call GetDailyReportSummary(:jobNumber)",
            nativeQuery = true
    )
    List<DailyReportSummaryDto> findSummaryByJobNumber(@Param("jobNumber") String jobNumber);
}
