package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.dto.dailyReport.DrEmployeeHoursDto;
import com.ck.wi.model.entity.dailyReport.DrEmployee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
