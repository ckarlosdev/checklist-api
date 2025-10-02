package com.ck.wi.controller.maintenance;

import com.ck.wi.model.dto.maintenance.MaintenanceDto;
import com.ck.wi.model.entity.maintenance.Maintenance;
import com.ck.wi.service.maintenance.IMaintenance;
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
public class MaintenanceController {

    @Autowired
    private IMaintenance maintenanceService;

    @PostMapping("maintenance")
    public Maintenance save(@RequestBody MaintenanceDto maintenanceDto){
        return maintenanceService.save(maintenanceDto);
    }

    @PutMapping("maintenance")
    public Maintenance update(@RequestBody MaintenanceDto maintenanceDto){
        return maintenanceService.save(maintenanceDto);
    }

    @GetMapping("maintenances")
    public List<MaintenanceDto> getAll(){
        return maintenanceService.findAll();
    }
}
