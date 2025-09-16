package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentDao extends CrudRepository<Equipment, Integer> {
}
