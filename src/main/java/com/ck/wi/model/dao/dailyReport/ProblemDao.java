package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.Problem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProblemDao extends CrudRepository<Problem, Integer> {
//    List<Problem> findByDrEquipmentsIdAndStatus(Integer drEquipmentsId, String status);

//    List<Problem> findByDrEquipmentsIdIn(List<Integer> drEquipmentsIds);

    @Query(
            value = "select * from problems where dr_equipments_id = :drEquipmentId and status = '1'",
            nativeQuery = true
    )
    List<Problem> findByDrEquipmentsIdAndStatus(@Param("drEquipmentId") Integer drEquipmentId);

    @Query(
            value = "select * from problems where dr_equipments_id in (:drEquipmentId) and status = '1'",
            nativeQuery = true
    )
    List<Problem> findByDrEquipmentsIdIn(@Param("drEquipmentId") List<Integer> drEquipmentId);

}
