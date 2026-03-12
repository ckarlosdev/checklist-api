package com.ck.wi.service.impl;

import com.ck.wi.model.dao.EquipmentDao;
import com.ck.wi.model.dto.EquipmentDto;
import com.ck.wi.model.entity.Equipment;
import com.ck.wi.service.IEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipmentImpl implements IEquipment {

    @Autowired
    private EquipmentDao equipmentDao;

    @Transactional
    @Override
    public Equipment save(EquipmentDto equipmentDto) {
        Equipment equipment;

        if(equipmentDto.getEquipmentsId() != null){
            equipment = equipmentDao.findById(equipmentDto.getEquipmentsId())
                    .orElseThrow(() -> new RuntimeException("Equipment not found"));
        }else{
            equipment = new Equipment();
            equipment.setCreatedBy(equipmentDto.getUser());
        }

        equipment.setFamily(equipmentDto.getFamily());
        equipment.setNumber(equipmentDto.getNumber());
        equipment.setName(equipmentDto.getName());
        equipment.setManufacturing(equipmentDto.getManufacturing());
        equipment.setModel(equipmentDto.getModel());
        equipment.setYear(equipmentDto.getYear());
        equipment.setPurchaseDate(equipmentDto.getPurchaseDate());
        equipment.setStatus(equipmentDto.getStatus());
        equipment.setCondition(equipmentDto.getCondition());
        equipment.setSerialNumber(equipmentDto.getSerialNumber());
        equipment.setHour(equipmentDto.getHour());
        equipment.setEquipmentStatus("1");
        equipment.setUpdatedBy(equipmentDto.getUser());

        return equipmentDao.save(equipment);

    }

    @Transactional
    @Override
    public void updateOdometer(Integer equipmentId, float odometer){
        Equipment equipment = equipmentDao.findById(equipmentId)
                .orElse(null);

        if(equipment != null && equipment.getHour() > odometer){
            equipment.setHour(odometer);
            equipmentDao.save(equipment);
        }
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

    @Transactional
    @Override
    public List<Equipment> findAll() {
        return (List<Equipment>) equipmentDao.findAll();
    }
}
