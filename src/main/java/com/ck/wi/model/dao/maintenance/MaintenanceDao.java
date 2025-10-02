package com.ck.wi.model.dao.maintenance;

import com.ck.wi.model.entity.maintenance.Maintenance;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceDao extends CrudRepository<Maintenance, Integer> {

    @Modifying
    @Query("UPDATE Maintenance m SET m.maintenancesStatus = '0' WHERE m.equipmentsId = :equipmentsId AND m.maintenancesStatus = '1'")
    void invalidatePreviousRecords(@Param("equipmentsId") Integer equipmentsId);
}
