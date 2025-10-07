package com.ck.wi.controller.maintenance;

import com.ck.wi.model.dto.maintenance.MaintenanceHistoryCreateDto;
import com.ck.wi.model.dto.maintenance.MaintenanceHistoryDto;
import com.ck.wi.service.maintenance.IMaintenanceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class MaintenanceHistoryController {

    @Autowired
    private IMaintenanceHistory maintenanceHistoryService;

    @PostMapping("maintenanceHistory")
    public MaintenanceHistoryDto save(@RequestBody MaintenanceHistoryCreateDto maintenanceHistoryCreateDto){
        return maintenanceHistoryService.save(maintenanceHistoryCreateDto);
    }

    @GetMapping("maintenancesHistory/{maintenancesId}")
    public List<MaintenanceHistoryDto> getMaintenanceById(@PathVariable Integer maintenancesId){
        return maintenanceHistoryService.getMaintenancesById(maintenancesId);
    }
}
