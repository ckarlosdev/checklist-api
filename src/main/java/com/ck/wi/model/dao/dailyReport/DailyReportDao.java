package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.DailyReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

import java.util.Date;
import java.util.List;

public interface DailyReportDao extends CrudRepository<DailyReport, Integer> {
    List<DailyReport> findByNumberAndStatus(String number, String status);

    List<DailyReport> findByNumberIn(List<String> number);

//    DailyReport findByNumberAndDate(String number, Date date);

    @Query(
            value = "SELECT * FROM daily_reports WHERE number = :number AND DATE(date) = :date",
            nativeQuery = true
    )
    DailyReport findByNumberAndDate(@Param("number") String number, @Param("date") LocalDate date);
}
