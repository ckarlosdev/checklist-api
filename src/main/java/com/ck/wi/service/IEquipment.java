package com.ck.wi.service;

import com.ck.wi.model.dto.EquipmentDto;
import com.ck.wi.model.entity.Equipment;

import java.util.List;

public interface IEquipment {

    Equipment save(EquipmentDto equipmentDto);

    void updateOdometer(Integer equipmentId, float odometer);

    Equipment findById(Integer id);

    void delete(Equipment equipment);

    List<Equipment> findAll();
}
