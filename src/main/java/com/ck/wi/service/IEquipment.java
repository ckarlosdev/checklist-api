package com.ck.wi.service;

import com.ck.wi.model.entity.Equipment;

public interface IEquipment {

    Equipment save(Equipment equipment);

    Equipment findById(Integer id);

    void delete(Equipment equipment);
}
