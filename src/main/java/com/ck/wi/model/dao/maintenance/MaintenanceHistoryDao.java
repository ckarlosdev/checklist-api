package com.ck.wi.model.dao.maintenance;

import com.ck.wi.model.entity.maintenance.MaintenanceHistory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceHistoryDao extends CrudRepository<MaintenanceHistory, Integer> {
    MaintenanceHistory findByMaintenancesIdAndMeStatus(Integer maintenancesId, String status);

    List<MaintenanceHistory> findByMaintenancesIdOrderByMaintenanceDateDesc(Integer maintenanceId);

    @Modifying
    @Query("UPDATE MaintenanceHistory mh SET mh.meStatus = '0' " +
            "WHERE mh.maintenancesId IN (SELECT m.maintenancesId FROM Maintenance m WHERE m.equipmentsId = :equipmentsId) " +
            "AND mh.meStatus = '1'")
    void invalidatePreviousRecordsByEquipmentId(@Param("equipmentsId") Integer equipmentsId);


}
