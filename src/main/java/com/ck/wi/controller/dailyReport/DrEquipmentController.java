package com.ck.wi.controller.dailyReport;

import com.ck.wi.model.dto.dailyReport.DrEquipmentDto;
import com.ck.wi.model.entity.dailyReport.DrEquipment;
import com.ck.wi.service.dailyReport.IDrEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com"
})
@RestController
@RequestMapping("/api/v1")
public class DrEquipmentController {

    @Autowired
    private IDrEquipment drEquipmentService;

    @GetMapping("drEquipment/dailyReport/{dailyReportId}")
    public List<DrEquipmentDto> getDrEquipmentByDailyReportId(@PathVariable Integer dailyReportId){
        List<DrEquipment> drEquipments = drEquipmentService.findByDailyReportId(dailyReportId);

        return drEquipments.stream()
                .map(drEquipment ->
                        DrEquipmentDto.builder()
                                .drEquipmentsId(drEquipment.getDrEquipmentsId())
                                .dailyReportId(drEquipment.getDailyReportId())
                                .equipmentsId(drEquipment.getEquipmentsId())
                                .employeesId(drEquipment.getEmployeesId())
                                .operator(drEquipment.getOperator())
                                .type(drEquipment.getType())
                                .number(drEquipment.getNumber())
                                .name(drEquipment.getName())
                                .serialNumber(drEquipment.getSerialNumber())
                                .initialHour(drEquipment.getInitialHour())
                                .newHour(drEquipment.getNewHour())
                                .status(drEquipment.getStatus())
                                .build())
                .collect(Collectors.toList());
    }
}
