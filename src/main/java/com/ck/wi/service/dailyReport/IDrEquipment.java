package com.ck.wi.service.dailyReport;

import com.ck.wi.model.dto.dailyReport.creation.DrEmployeeCreateDto;
import com.ck.wi.model.dto.dailyReport.creation.DrEquipmentCreateDto;
import com.ck.wi.model.entity.dailyReport.DrEmployee;
import com.ck.wi.model.entity.dailyReport.DrEquipment;

import java.util.List;

public interface IDrEquipment {
    List<DrEquipment> findByDailyReportId(Integer dailyReportId);

    List<DrEquipment> findByDailyReportIds(List<Integer> dailyReportIds);

    List<Integer> findDrEquipmentIdsByDrId(Integer drId);

    List<DrEquipmentCreateDto> getLastReportEquipments(String jobNum);
}
