package com.ck.wi.service.maintenance;

import com.ck.wi.model.dto.maintenance.MaintenanceDto;
import com.ck.wi.model.entity.maintenance.Maintenance;

import java.util.List;

public interface IMaintenance {
    Maintenance save(MaintenanceDto maintenanceDto);

    List<MaintenanceDto> findAll();
}
