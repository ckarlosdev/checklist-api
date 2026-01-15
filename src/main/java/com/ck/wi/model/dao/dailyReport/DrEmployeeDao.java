package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.dto.dailyReport.DrEmployeeHoursDto;
import com.ck.wi.model.entity.dailyReport.DrEmployee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DrEmployeeDao extends CrudRepository<DrEmployee, Integer> {
    List<DrEmployee> findByDailyReportIdAndStatus(Integer dailyReportId, String status);

    List<DrEmployee> findByDailyReportIdIn(List<Integer> dailyReportIds);

    @Query(
            value = "select E.dr_employees_id AS drEmployeesId, " +
                    "E.out_hour AS outHour, " +
                    "E.in_hour AS inHour, " +
                    "TIMEDIFF(E.out_hour, E.in_hour) AS timeDifference, " +
                    "    TIME_TO_SEC(TIMEDIFF(E.out_hour, E.in_hour)) / 3600 AS totalHoursDecimal, " +
                    "    E.lunch " +
                    "from daily_reports DR " +
                    "inner join dr_employees E on DR.daily_report_id=E.daily_report_id " +
                    "where DR.number = :jobNumber ",
            nativeQuery = true
    )
    List<DrEmployeeHoursDto> findHoursByNumber(@Param("jobNumber") String jobNumber);

    @Query(value = " SELECT ( " +
            " SUM(TIME_TO_SEC(TIMEDIFF(E.out_hour, E.in_hour))) - " +
            " SUM(CASE WHEN E.lunch = 'true' OR E.lunch = 'TRUE' OR E.lunch = '1' " +
            "        THEN 1800 ELSE 0 END)) / 3600 AS grandTotalHours " +
            " FROM daily_reports DR " +
            " INNER JOIN dr_employees E ON DR.daily_report_id = E.daily_report_id " +
            " WHERE E.status = '1' " +
            "  AND DR.status = '1' " +
            "  AND DR.number = :jobNumber " +
            "  AND DR.date != :excludeDate; ", nativeQuery = true)
    double findTotalHourLessADate(
            @Param("jobNumber") String jobNumber,
            @Param("excludeDate") LocalDate excludeDate);

    @Query(value = "SELECT e.* " +
            "FROM dr_employees e " +
            "WHERE e.status = '1' " +
            "AND e.daily_report_id = ( " +
            "    SELECT dr.daily_report_id " +
            "    FROM daily_reports dr " +
            "    INNER JOIN dr_employees e2 ON dr.daily_report_id = e2.daily_report_id " +
            "    WHERE dr.number = :jobNumber " +
            "      AND e2.status = '1' " +
            "    ORDER BY dr.date DESC, dr.daily_report_id DESC " +
            "    LIMIT 1 " +
            ");", nativeQuery = true)
    List<DrEmployee> findEmployeesFromLastReport(@Param("jobNumber") String jobNumber);
}
