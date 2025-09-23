package com.ck.wi.model.dao.odometer;

import com.ck.wi.model.entity.Equipment;
import com.ck.wi.model.entity.odometer.Odometer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OdometerDao extends CrudRepository<Odometer, Integer> {
    Optional<Odometer> findByEquipment(Equipment equipment);
}
