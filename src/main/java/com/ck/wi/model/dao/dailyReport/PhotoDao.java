package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.DailyReport;
import com.ck.wi.model.entity.dailyReport.Photo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PhotoDao extends CrudRepository<Photo, Integer> {
    List<Photo> findByDailyReportIdAndStatus(Integer dailyReportId, String status);

    List<Photo> findByDailyReportIdIn(List<Integer> dailyReportIds);

    @Query(
            value = "select * from photos where type = :typeReport and daily_report_id = :dailyReportId and status='1' ",
            nativeQuery = true
    )
    List<Photo> findByTypeAndId(@Param("typeReport") String typeReport, @Param("dailyReportId") Integer dailyReportId);
}
