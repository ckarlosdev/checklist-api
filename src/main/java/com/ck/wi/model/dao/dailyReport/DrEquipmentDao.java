package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.DrEquipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DrEquipmentDao extends CrudRepository<DrEquipment, Integer> {
    List<DrEquipment> findByDailyReportIdAndStatus(Integer dailyReportId, String status);

    List<DrEquipment> findByDailyReportIdIn(List<Integer> dailyReportIds);
}
