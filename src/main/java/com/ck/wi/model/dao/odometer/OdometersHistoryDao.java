package com.ck.wi.model.dao.odometer;

import com.ck.wi.model.entity.odometer.OdometersHistory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OdometersHistoryDao extends CrudRepository<OdometersHistory, Integer> {

    List<OdometersHistory> findByOdometersId(Integer odometerId);

    OdometersHistory findByOdometersIdAndOdometersStatus(Integer odometerId, String status);


    @Modifying
    @Query("UPDATE OdometersHistory oh SET oh.odometersStatus = '0' WHERE oh.odometersId = :odometersId AND oh.odometersStatus = '1'")
    void invalidatePreviousRecords(@Param("odometersId") Integer odometersId);

//    @Modifying
//    @Query("UPDATE OdometersHistory oh SET oh.odometersStatus = '0' " +
//            "WHERE oh.odometersId IN (SELECT o.odometersId FROM Odometer o WHERE o.equipmentsId = :equipmentsId) " +
//            "AND oh.odometersStatus = '1'")
//    void invalidatePreviousRecordsByEquipmentId(@Param("equipmentsId") Integer equipmentsId);

    @Modifying
    @Query("UPDATE OdometersHistory oh SET oh.odometersStatus = '0' " +
            "WHERE oh.odometersId IN (SELECT o.odometersId FROM Odometer o WHERE o.equipment.id = :equipmentsId) " +
            "AND oh.odometersStatus = '1'")
    void invalidatePreviousRecordsByEquipmentId(@Param("equipmentsId") Integer equipmentsId);
}
