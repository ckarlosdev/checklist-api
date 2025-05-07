package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Equipment;
import org.springframework.data.repository.CrudRepository;

public interface EquipmentDao extends CrudRepository<Equipment, Integer> {
}
