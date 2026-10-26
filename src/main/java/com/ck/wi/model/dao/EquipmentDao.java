package com.ck.wi.model.dao;

import com.ck.wi.model.dto.EquipmentDto;
import com.ck.wi.model.dto.EquipmentResponseDto;
import com.ck.wi.model.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentDao extends JpaRepository<Equipment, Integer> {

    @Query("""
        SELECT new com.ck.wi.model.dto.EquipmentDto(
            e.equipmentsId,
            e.family,
            e.number,
            e.name,
            e.manufacturing,
            e.model,
            e.year,
            e.purchaseDate,
            e.status,
            e.condition,
            e.serialNumber,
            e.hour,
            e.createdBy
        )
        FROM Equipment e
        """)
    List<EquipmentDto> findAllEquipmentDtos();

    @Query("SELECT new com.ck.wi.model.dto.EquipmentResponseDto(" +
            "e.equipmentsId, e.family, e.number, e.name, e.hour, d.subtype, d.licensePlate) " +
            "FROM Equipment e LEFT JOIN e.equipmentDetail d " +
            "WHERE e.equipmentStatus = '1'")
    List<EquipmentResponseDto> obtenerEquipmentsConDetalles();
}
