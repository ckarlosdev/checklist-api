package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.DrRental;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrRentalDao extends CrudRepository<DrRental, Integer> {
    List<DrRental> findByDailyReportIdAndRentalsStatus(Integer dailyReportId, String status);

    @Query(value = "SELECT r.* " +
            "FROM dr_rentals r " +
            "WHERE r.rentals_status='1' " +
            "AND r.daily_report_id = ( " +
            "    SELECT d.daily_report_id  " +
            "    FROM daily_reports d " +
            "    INNER JOIN dr_rentals r ON r.daily_report_id=d.daily_report_id " +
            "    WHERE d.number = :jobNumber " +
            "    AND r.rentals_status='1' AND d.status = '1' " +
            "    ORDER BY d.date DESC, d.daily_report_id DESC " +
            "    LIMIT 1 " +
            ");", nativeQuery = true)
    List<DrRental> findRentalsFromLastReport(@Param("jobNumber") String jobNumber);
}
