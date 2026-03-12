package com.ck.wi.controller;

import com.ck.wi.model.dto.EquipmentDto;
import com.ck.wi.model.entity.Equipment;
import com.ck.wi.service.IEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "https://oleo-soft.com", methods = {RequestMethod.GET, RequestMethod.POST})
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class EquipmentController {

    @Autowired
    private IEquipment equipmentService;

    @PostMapping("equipment")
    public EquipmentDto create(@RequestBody EquipmentDto equipmentDto){
        Equipment equipmentSaved = equipmentService.save(equipmentDto);

        return EquipmentDto.builder()
                .equipmentsId(equipmentSaved.getEquipmentsId())
                .family(equipmentSaved.getFamily())
                .number(equipmentSaved.getNumber())
                .name(equipmentSaved.getName())
                .manufacturing(equipmentSaved.getManufacturing())
                .model(equipmentSaved.getModel())
                .year(equipmentSaved.getYear())
                .purchaseDate(equipmentSaved.getPurchaseDate())
                .status(equipmentSaved.getStatus())
                .condition(equipmentSaved.getCondition())
                .serialNumber(equipmentSaved.getSerialNumber())
                .hour(equipmentSaved.getHour())
                .user(equipmentSaved.getUpdatedBy())
                .build();
    }

    @PutMapping("equipment")
    public EquipmentDto update(@RequestBody EquipmentDto equipmentDto){
        Equipment equipmentUpdated = equipmentService.save(equipmentDto);

        return EquipmentDto.builder()
                .equipmentsId(equipmentUpdated.getEquipmentsId())
                .family(equipmentUpdated.getFamily())
                .number(equipmentUpdated.getNumber())
                .name(equipmentUpdated.getName())
                .manufacturing(equipmentUpdated.getManufacturing())
                .model(equipmentUpdated.getModel())
                .year(equipmentUpdated.getYear())
                .purchaseDate(equipmentUpdated.getPurchaseDate())
                .status(equipmentUpdated.getStatus())
                .condition(equipmentUpdated.getCondition())
                .serialNumber(equipmentUpdated.getSerialNumber())
                .hour(equipmentUpdated.getHour())
                .user(equipmentUpdated.getUpdatedBy())
                .build();
    }

    @DeleteMapping("equipment/{id}")
    public void delete(@PathVariable Integer id){
        Equipment equipment = equipmentService.findById(id);
        equipmentService.delete(equipment);
    }

    @GetMapping("equipment/{id}")
    public Equipment showById(@PathVariable Integer id){

        return equipmentService.findById(id);
    }

    @GetMapping("equipments")
    public List<Equipment> showAll(){

        return equipmentService.findAll();
    }
}
