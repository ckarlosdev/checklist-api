package com.ck.wi.model.dao.assignment;

import com.ck.wi.model.dto.dashboard.CalendarEventDTO;
import com.ck.wi.model.entity.assignment.Assignment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentDao extends CrudRepository<Assignment, Integer> {
//WHERE D.date BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE)
    @Query(value = "WITH RawData AS ( " +
            "    SELECT  " +
            "        D.daily_report_id, " +
            "        D.date, " +
            "        J.jobs_id, " +
            "        E.in_hour, " +
            "        E.out_hour, " +
            "        SUM(IF(E.title = 'Labor', 10, 1)) OVER(PARTITION BY D.daily_report_id, E.in_hour, E.out_hour) as puntos, " +
            "        COUNT(*) OVER(PARTITION BY D.daily_report_id, E.in_hour, E.out_hour) as total_personas, " +
            "        (TIME_TO_SEC(E.out_hour) - TIME_TO_SEC(E.in_hour)) as dur " +
            "    FROM ( " +
            "        SELECT MIN(daily_report_id) as daily_report_id, number, date, status " +
            "        FROM daily_reports " +
            "        WHERE status='1' " +
            "        GROUP BY number, date " +
            "    ) D " +
            "    INNER JOIN jobs J ON D.number = J.number " +
            "    INNER JOIN dr_employees E ON E.daily_report_id = D.daily_report_id " +
            "    WHERE D.date BETWEEN :startDate AND :endDate " +
            "      AND E.status = 1 " +
            "), " +
            "MainEvents AS ( " +
            "    SELECT daily_report_id, date, jobs_id, in_hour, out_hour " +
            "    FROM ( " +
            "        SELECT *, ROW_NUMBER() OVER( " +
            "            PARTITION BY jobs_id, date ORDER BY puntos DESC, total_personas DESC, dur DESC " +
            "        ) as ranking " +
            "        FROM RawData " +
            "    ) AS Sub " +
            "    WHERE ranking = 1 " +
            ") " +
            "SELECT  " +
            "    M.jobs_id, " +
            "    M.date, " +
            "    M.in_hour AS start, " +
            "    M.out_hour AS end, " +
            "    M.daily_report_id AS daily, " +
            "    P.min_id AS pretask, " +
            "    CL.min_id AS checklist, " +
            "    S.min_id AS silica, " +
            "    D.min_id AS demo " +
            "FROM MainEvents M " +
            "LEFT JOIN ( " +
            "    SELECT jobs_id, date, MIN(pre_tasks_id) as min_id  " +
            "    FROM pre_tasks WHERE status='1' GROUP BY jobs_id, date " +
            ") P ON M.jobs_id = P.jobs_id AND M.date = P.date " +
            "LEFT JOIN ( " +
            "    SELECT jobs_id, date, MIN(equipments_google_checklists_id) as min_id  " +
            "    FROM equipments_google_checklists WHERE status='1' GROUP BY jobs_id, date " +
            ") CL ON M.jobs_id = CL.jobs_id AND M.date = CL.date " +
            "LEFT JOIN ( " +
            "    SELECT jobs_id, event_date, MIN(silica_id) as min_id  " +
            "    FROM silica WHERE silica_status='1' GROUP BY jobs_id, event_date " +
            ") S ON M.jobs_id = S.jobs_id AND M.date = S.event_date " +
            "LEFT JOIN ( " +
            "    SELECT jobs_id, checklist_date, MIN(demo_checklists_id) as min_id  " +
            "    FROM demo_checklists WHERE demo_checklists_status='1' GROUP BY jobs_id, checklist_date " +
            ") D ON M.jobs_id = D.jobs_id AND M.date = D.checklist_date " +
            "ORDER BY M.date ASC, M.in_hour ASC;", nativeQuery = true)
    List<Object[]> findRawCalendarData(
            @Param("startDate") java.time.LocalDate startDate,
            @Param("endDate") java.time.LocalDate endDate
    );


    @Query(value = "WITH RawData AS ( " +
            "               SELECT  " +
            "                   D.daily_report_id, " +
            "                   D.date, " +
            "                   J.jobs_id, " +
            "                   E.in_hour, " +
            "                   E.out_hour, " +
            "                   SUM(IF(E.title = 'Labor', 10, 1)) OVER(PARTITION BY D.daily_report_id, E.in_hour, E.out_hour) as puntos, " +
            "                   COUNT(*) OVER(PARTITION BY D.daily_report_id, E.in_hour, E.out_hour) as total_personas, " +
            "                   (TIME_TO_SEC(E.out_hour) - TIME_TO_SEC(E.in_hour)) as dur " +
            "               FROM ( " +
            "                   SELECT MIN(daily_report_id) as daily_report_id, number, date, status " +
            "                   FROM daily_reports " +
            "                   WHERE status='1' " +
            "                   GROUP BY number, date " +
            "               ) D " +
            "               INNER JOIN jobs J ON D.number = J.number " +
            "               INNER JOIN dr_employees E ON E.daily_report_id = D.daily_report_id " +
            "               WHERE J.jobs_id = :jobId AND E.status = '1' " +
            "            ), " +
            "            MainEvents AS ( " +
            "               SELECT daily_report_id, date, jobs_id, in_hour, out_hour " +
            "               FROM ( " +
            "                   SELECT *, ROW_NUMBER() OVER( " +
            "                       PARTITION BY jobs_id, date ORDER BY puntos DESC, total_personas DESC, dur DESC " +
            "                   ) as ranking " +
            "                   FROM RawData " +
            "               ) AS Sub " +
            "               WHERE ranking = 1 " +
            "            ) " +
            "            SELECT  " +
            "               M.jobs_id, " +
            "               M.date, " +
            "               M.in_hour AS start, " +
            "               M.out_hour AS end, " +
            "               M.daily_report_id AS daily, " +
            "               P.min_id AS pretask, " +
            "               CL.min_id AS checklist, " +
            "               S.min_id AS silica, " +
            "               D.min_id AS demo " +
            "            FROM MainEvents M " +
            "            LEFT JOIN ( " +
            "               SELECT jobs_id, date, MIN(pre_tasks_id) as min_id  " +
            "               FROM pre_tasks WHERE status='1' GROUP BY jobs_id, date " +
            "            ) P ON M.jobs_id = P.jobs_id AND M.date = P.date " +
            "            LEFT JOIN ( " +
            "               SELECT jobs_id, date, MIN(equipments_google_checklists_id) as min_id  " +
            "               FROM equipments_google_checklists WHERE status='1' GROUP BY jobs_id, date " +
            "            ) CL ON M.jobs_id = CL.jobs_id AND M.date = CL.date " +
            "            LEFT JOIN ( " +
            "               SELECT jobs_id, event_date, MIN(silica_id) as min_id  " +
            "               FROM silica WHERE silica_status='1' GROUP BY jobs_id, event_date " +
            "            ) S ON M.jobs_id = S.jobs_id AND M.date = S.event_date " +
            "            LEFT JOIN ( " +
            "               SELECT jobs_id, checklist_date, MIN(demo_checklists_id) as min_id  " +
            "               FROM demo_checklists WHERE demo_checklists_status='1' GROUP BY jobs_id, checklist_date " +
            "            ) D ON M.jobs_id = D.jobs_id AND M.date = D.checklist_date " +
            "            ORDER BY M.date ASC, M.in_hour ASC; ", nativeQuery = true)
    List<Object[]> findRawCalendarDataById(
            @Param("jobId") Integer jobId
    );


//    @Query("SELECT DISTINCT a FROM Assignment a " +
//            "LEFT JOIN FETCH a.assignmentJobs aj " +
//            "LEFT JOIN FETCH aj.assignmentEmployees ae " +
//            "LEFT JOIN FETCH a.assignmentAbsence ab " +
//            "WHERE a.assignmentsId = :id AND a.assignmentStatus = '1' " +
//            "AND aj.ajStatus = '1' AND ae.aeStatus = '1' AND ab.absenceStatus = '1' ")
//    Optional<Assignment> findFullAssignmentById(@Param("id") Integer id);

    @Query("SELECT DISTINCT a FROM Assignment a " +
            "LEFT JOIN FETCH a.assignmentJobs aj " +
            "LEFT JOIN FETCH aj.assignmentEmployees ae " + // 1. Corregido a plural
            "LEFT JOIN FETCH a.assignmentAbsences ab " +
            "WHERE a.assignmentsId = :id " +
            "AND a.assignmentStatus = '1' " +
            "AND (aj IS NULL OR aj.ajStatus = '1') ")
    Optional<Assignment> findFullAssignmentById(@Param("id") Integer id);

    List<Assignment> findByAssignmentStatus(String status);

}
