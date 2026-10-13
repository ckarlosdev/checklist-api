package com.ck.wi.model.dao;

import com.ck.wi.model.entity.EquipmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentDetailDao extends JpaRepository<EquipmentDetail, Long> {
    Optional<EquipmentDetail> findByEquipment_EquipmentsId(Integer equipmentId);
}
