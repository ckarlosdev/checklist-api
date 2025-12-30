package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.dto.dailyReport.DailyReportGralDto;
import com.ck.wi.model.dto.dailyReport.DailyReportSummaryDto;
import com.ck.wi.model.entity.dailyReport.DailyReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Procedure(procedureName = "GetDailyReportGral")
    List<DailyReportGralDto> getSummaryByNumberAndStatus(
            @Param("report_number") String reportNumber
    );

    @Query(value = "select count(1) " +
            " from (select date, count(1) " +
            " from daily_reports " +
            " where status='1' and number='2408' and date!='2025-09-27' " +
            " group by date " +
            " order by date desc) as T;", nativeQuery = true)
    Integer getTotalDaysByJobNumber(
            @Param("jobNumber") String jobNumber,
            @Param("excludeDate") LocalDate excludeDate
    );

}
