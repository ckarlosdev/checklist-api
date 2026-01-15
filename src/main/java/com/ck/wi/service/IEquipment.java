package com.ck.wi.service;

import com.ck.wi.model.entity.Equipment;

import java.util.List;

public interface IEquipment {

    Equipment save(Equipment equipment);

    void updateOdometer(Integer equipmentId, float odometer);

    Equipment findById(Integer id);

    void delete(Equipment equipment);

    List<Equipment> findAll();
}
