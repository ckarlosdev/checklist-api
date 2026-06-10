package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.dto.dailyReport.DailyReportGralDto;
import com.ck.wi.model.dto.dailyReport.DailyReportSummaryDto;
import com.ck.wi.model.dto.dailyReport.EmployeeHoursDTO;
import com.ck.wi.model.dto.dashboard.TimelineDataDto;
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
            " where status='1' and number= :jobNumber and date!= :excludeDate " +
            " group by date " +
            " order by date desc) as T;", nativeQuery = true)
    Integer getTotalDaysByJobNumber(
            @Param("jobNumber") String jobNumber,
            @Param("excludeDate") LocalDate excludeDate
    );

    @Query(value = """
        SELECT 
            E.employees_id AS employeesId,
            DE.name AS name,
            SUM(
                (
                    (
                        TIME_TO_SEC(DE.out_hour) - TIME_TO_SEC(DE.in_hour) +
                        IF(TIME_TO_SEC(DE.out_hour) < TIME_TO_SEC(DE.in_hour), 86400, 0)
                    ) / 3600
                ) - IF(DE.lunch = 'true', 0.5, 0)
            ) AS totalHrs
        FROM daily_reports D
        INNER JOIN dr_employees DE ON D.daily_report_id = DE.daily_report_id
        INNER JOIN employees E ON DE.employees_id = E.employee_number
        WHERE D.date BETWEEN :startDate AND :endDate 
            AND D.status = '1' 
            AND DE.status = '1'
        GROUP BY E.employees_id, DE.name
    """, nativeQuery = true)
    List<EmployeeHoursDTO> findEmployeeHoursSummary(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    @Query(
            value = " select date as drDate, foreman " +
                    " from daily_reports " +
                    " where number= :jobNumber and status='1' " +
                    " order by date desc; ",
            nativeQuery = true
    )
    List<TimelineDataDto> findDateByJobNumber(@Param("jobNumber") String jobNumber);

}
