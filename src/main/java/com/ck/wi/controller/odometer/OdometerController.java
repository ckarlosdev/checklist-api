package com.ck.wi.controller.odometer;

import com.ck.wi.model.dto.odometer.OdometerDto;
import com.ck.wi.model.dto.odometer.OdometerSaveDto;
import com.ck.wi.model.dto.odometer.OdometerSearchDto;
import com.ck.wi.model.entity.odometer.Odometer;
import com.ck.wi.service.odometer.IOdometer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class OdometerController {

    @Autowired
    private IOdometer odometerService;

    @PostMapping("odometer")
    public OdometerDto create(@RequestBody OdometerDto odometerDto){
        return odometerService.processAndSaveOdometer(odometerDto);
    }

    @PostMapping("odometer/equipment")
    public void create(@RequestBody OdometerSaveDto odometerSaveDto){
        odometerService.saveOdometerByEquipmentId(odometerSaveDto);
    }

    @PutMapping("odometer")
    public OdometerDto update(@RequestBody OdometerDto odometerDto){
        return odometerService.processAndSaveOdometer(odometerDto);
    }

    @GetMapping("odometer/equipment/{equipmentsId}")
    public OdometerDto findByEquipmentId(@PathVariable Integer equipmentsId){
        return odometerService.findByEquipmentsId(equipmentsId);
    }

    @GetMapping("odometers/{odometerId}")
    public OdometerDto findById(@PathVariable Integer odometerId){
        return odometerService.findOdometerById(odometerId);
    }

    @GetMapping("odometers")
    public List<OdometerSearchDto> findAll(){
        List<Odometer>  odometerList = odometerService.findAll();

        return odometerList.stream()
                .map(this::convertToDto) // Mapea cada Odometer a un OdometerDto
                .collect(Collectors.toList());
    }

    private OdometerSearchDto convertToDto(Odometer odometer) {

        return OdometerSearchDto.builder()
                .odometersId(odometer.getOdometersId())
                .equipmentsId(odometer.getEquipment().getEquipmentsId())
                .equipmentName(odometer.getEquipment().getName())
                .equipmentNumber(odometer.getEquipment().getNumber())
                .odometer(odometer.getOdometer())
                .build();
    }
}
