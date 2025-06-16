package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.DailyReport;
import com.ck.wi.model.entity.dailyReport.DrEquipment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrEquipmentDao extends CrudRepository<DrEquipment, Integer> {
    List<DrEquipment> findByDailyReportIdAndStatus(Integer dailyReportId, String status);

    List<DrEquipment> findByDailyReportIdIn(List<Integer> dailyReportIds);

    @Query(
            value = "select dr_equipments_id from dr_equipments where daily_report_id = :drId ",
            nativeQuery = true
    )
    List<Integer> findDrEquipmentIdsByDR(@Param("drId") Integer drId);
}
