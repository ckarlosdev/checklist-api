package com.ck.wi.service.maintenance;

import com.ck.wi.model.dto.maintenance.MaintenanceHistoryCreateDto;
import com.ck.wi.model.dto.maintenance.MaintenanceHistoryDto;

import java.util.List;

public interface IMaintenanceHistory {

    MaintenanceHistoryDto save(MaintenanceHistoryCreateDto maintenanceHistoryCreateDto);

    MaintenanceHistoryDto getByMaintenanceId(Integer maintenanceId);

    List<MaintenanceHistoryDto> getMaintenancesById(Integer maintenanceId);
}
