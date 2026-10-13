package com.ck.wi.model.dao;

import com.ck.wi.model.dto.EquipmentResponseDto;
import com.ck.wi.model.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentDao extends JpaRepository<Equipment, Integer> {

    @Query("SELECT new com.ck.wi.model.dto.EquipmentResponseDto(" +
            "e.equipmentsId, e.family, e.number, e.name, e.hour, d.subtype, d.licensePlate) " +
            "FROM Equipment e JOIN EquipmentDetail d ON e.equipmentsId = d.equipment.equipmentsId " +
            "WHERE e.equipmentStatus = '1'")
    List<EquipmentResponseDto> findAllWithDetails();
}
