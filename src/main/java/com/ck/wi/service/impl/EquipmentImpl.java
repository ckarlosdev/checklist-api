package com.ck.wi.service.impl;

import com.ck.wi.model.dao.EquipmentDao;
import com.ck.wi.model.entity.Equipment;
import com.ck.wi.service.IEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipmentImpl implements IEquipment {

    @Autowired
    private EquipmentDao equipmentDao;

    @Transactional
    @Override
    public Equipment save(Equipment equipment) {
        return equipmentDao.save(equipment);
    }

    @Transactional(readOnly = true)
    @Override
    public Equipment findById(Integer id) {
        return equipmentDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Equipment equipment) {
        equipmentDao.delete(equipment);
    }
}
