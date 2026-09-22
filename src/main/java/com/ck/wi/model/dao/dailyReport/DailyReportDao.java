package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.dto.dailyReport.DailyReportGralDto;
import com.ck.wi.model.dto.dailyReport.DailyReportSummaryDto;
import com.ck.wi.model.dto.dailyReport.EmployeeHoursDTO;
import com.ck.wi.model.dto.dailyReport.dashboard.DailyReportDetailProjection;
import com.ck.wi.model.dto.dailyReport.dashboard.EmployeeSummaryProjection;
import com.ck.wi.model.dto.dailyReport.dashboard.EquipmentSummaryProjection;
import com.ck.wi.model.dto.dashboard.summaryDetails.DumpsterSummaryDTO;
import com.ck.wi.model.dto.dashboard.summaryDetails.PhotoSummaryDTO;
import com.ck.wi.model.dto.dashboard.summaryDetails.RentalSummaryDTO;
import com.ck.wi.model.dto.dashboard.summaryDetails.ToolSummaryDTO;
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
            value = " select daily_report_id as drId,  date as drDate, foreman " +
                    " from daily_reports " +
                    " where number= :jobNumber and status='1' " +
                    "order by date desc; " ,
            nativeQuery = true
    )
    List<Object[]> findDateByJobNumber(@Param("jobNumber") String jobNumber);

    @Query(value = """
        SELECT 
            J.jobs_id AS jobId,
            J.number AS jobNumber,
            J.name AS jobName,
            DE.title AS employeeTitle,
            DE.name AS employeeName,
            COUNT(DISTINCT DR.daily_report_id) AS totalReports,
            COUNT(DE.dr_employees_id) AS totalEmployeeEntries,
            ROUND(
                SUM(
                    (TIME_TO_SEC(TIMEDIFF(DE.out_hour, DE.in_hour)) / 3600.0) - 
                    IF(DE.lunch = 'true' OR DE.lunch = '1', 0.5, 0.0)
                ), 
            2) AS totalHours
        FROM jobs J
        INNER JOIN daily_reports DR ON DR.number = J.number
        INNER JOIN dr_employees DE ON DE.daily_report_id = DR.daily_report_id
        WHERE J.jobs_id = :jobId 
          AND DR.status = '1' 
          AND DE.status = '1'
        GROUP BY J.jobs_id, J.number, J.name, DE.title, DE.name
        ORDER BY totalHours DESC
        """, nativeQuery = true)
    List<EmployeeSummaryProjection> findEmployeeSummaryByJobId(@Param("jobId") Long jobId);

    // Query 2: Resumen de Equipos
    @Query(value = """
        SELECT 
            J.jobs_id AS jobId,
            J.number AS jobNumber,
            E.name AS equipmentName,
            COUNT(DISTINCT DR.daily_report_id) AS totalReports,
            COUNT(DE.dr_equipments_id) AS totalEquipmentEntries,
            ROUND(
                SUM(
                    GREATEST(DE.new_hour - DE.initial_hour, 0)
                ), 
            2) AS totalHours
        FROM jobs J
        INNER JOIN daily_reports DR ON DR.number = J.number 
        INNER JOIN dr_equipments DE ON DE.daily_report_id = DR.daily_report_id
        INNER JOIN equipments E ON E.equipments_id = DE.equipments_id
        WHERE J.jobs_id = :jobId 
          AND DR.status = '1' 
          AND DE.status = '1'
          AND DE.type = 'Equipment'
        GROUP BY J.jobs_id, J.number, E.name
        """, nativeQuery = true)
    List<EquipmentSummaryProjection> findEquipmentSummaryByJobId(@Param("jobId") Long jobId);

    // Query 3: Detalle por Rango de Fechas
    @Query(value = """
        SELECT 
            J.jobs_id AS jobId,
            J.number AS jobNumber, 
            J.name AS jobName,
            DR.daily_report_id AS dailyReportId,
            DR.foreman AS foreman, 
            DR.date AS reportDate,
            DE.dr_employees_id AS drEmployeesId,
            DE.name AS employeeName, 
            DE.title AS employeeTitle, 
            DE.in_hour AS inHour, 
            DE.out_hour AS outHour, 
            DE.lunch AS lunch,
            ROUND(
                (TIME_TO_SEC(TIMEDIFF(DE.out_hour, DE.in_hour)) / 3600.0) - 
                IF(DE.lunch = 'true' OR DE.lunch = '1', 0.5, 0.0), 
            2) AS hoursWorked
        FROM jobs J
        INNER JOIN daily_reports DR ON DR.number = J.number
        INNER JOIN dr_employees DE ON DE.daily_report_id = DR.daily_report_id
        WHERE J.jobs_id = :jobId 
          AND DR.status = '1' 
          AND DE.status = '1'
          AND DR.date BETWEEN :startDate AND :endDate
        ORDER BY DR.date DESC, DE.title, DE.name
        """, nativeQuery = true)
    List<DailyReportDetailProjection> findDailyReportsByJobIdAndDateRange(
            @Param("jobId") Long jobId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );


    // 1. Query Tools Summary
    @Query("""
        SELECT new com.ck.wi.model.dto.dashboard.summaryDetails.ToolSummaryDTO(DR.date, T.name, SUM(T.qty))
        FROM Job J
        JOIN DailyReport DR ON DR.number = J.number
        JOIN Tool T ON T.dailyReportId = DR.dailyReportId
        WHERE J.id = :jobId
          AND DR.status = '1'
          AND T.status = '1'
          AND DR.date BETWEEN :startDate AND :endDate
        GROUP BY DR.date, T.name
        ORDER BY DR.date DESC
    """)
    List<ToolSummaryDTO> findToolsSummaryByJobId(@Param("jobId") Long jobId,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);

    // 2. Query Dumpsters Summary
    @Query("""
        SELECT new com.ck.wi.model.dto.dashboard.summaryDetails.DumpsterSummaryDTO(
            DR.date, D.sourceDumpster, D.sizeDumpster, D.typeDumpster, SUM(D.quantity)
        )
        FROM Job J
        JOIN DailyReport DR ON DR.number = J.number
        JOIN DrDumpster D ON D.dailyReportId = DR.dailyReportId
        WHERE J.id = :jobId
          AND DR.status = '1'
          AND D.dumpstersStatus = '1'
          AND DR.date BETWEEN :startDate AND :endDate
        GROUP BY DR.date, D.sourceDumpster, D.sizeDumpster, D.typeDumpster
        ORDER BY DR.date DESC
    """)
    List<DumpsterSummaryDTO> findDumpstersSummaryByJobId(@Param("jobId") Long jobId,
                                                         @Param("startDate") LocalDate startDate,
                                                         @Param("endDate") LocalDate endDate);

    // 3. Query Photos Summary
    @Query("""
        SELECT new com.ck.wi.model.dto.dashboard.summaryDetails.PhotoSummaryDTO(DR.date, P.type, P.folderId, COUNT(P.id))
        FROM Job J
        JOIN DailyReport DR ON DR.number = J.number
        JOIN Photo P ON P.dailyReportId = DR.dailyReportId
        WHERE J.id = :jobId
          AND DR.status = '1'
          AND P.status = '1'
          AND DR.date BETWEEN :startDate AND :endDate
        GROUP BY DR.date, P.type, P.folderId
        ORDER BY DR.date DESC
    """)
    List<PhotoSummaryDTO> findPhotosSummaryByJobId(@Param("jobId") Long jobId,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

    // 4. Query Rentals Summary
    @Query("""
        SELECT new com.ck.wi.model.dto.dashboard.summaryDetails.RentalSummaryDTO(
            DR.date, R.equipmentType, R.company, R.equipmentName
        )
        FROM Job J
        JOIN DailyReport DR ON DR.number = J.number
        JOIN DrRental R ON R.dailyReportId = DR.dailyReportId
        WHERE J.id = :jobId
          AND DR.status = '1'
          AND R.rentalsStatus = '1'
          AND DR.date BETWEEN :startDate AND :endDate
        GROUP BY DR.date, R.equipmentType, R.company, R.equipmentName
        ORDER BY DR.date DESC
    """)
    List<RentalSummaryDTO> findRentalsSummaryByJobId(@Param("jobId") Long jobId,
                                                     @Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);

}
