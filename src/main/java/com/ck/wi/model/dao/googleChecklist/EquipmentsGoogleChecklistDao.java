package com.ck.wi.model.dao.googleChecklist;

import com.ck.wi.model.entity.googleChecklist.EquipmentsGoogleChecklist;
import com.ck.wi.model.entity.googleChecklist.GoogleChecklist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EquipmentsGoogleChecklistDao extends CrudRepository<EquipmentsGoogleChecklist, Integer> {
    @Query(
            value = "SELECT * FROM equipments_google_checklists WHERE jobs_id = :jobsId AND DATE(date) = :date AND status='1' LIMIT 1;",
            nativeQuery = true
    )
    EquipmentsGoogleChecklist findByJobsIdAndDate(
            @Param("jobsId") Integer jobsId,
            @Param("date") LocalDate date);

    @Query(
            value = "SELECT * FROM equipments_google_checklists WHERE jobs_id = :jobsId AND status='1' ",
            nativeQuery = true
    )
    List<EquipmentsGoogleChecklist> findAllByJobId(
            @Param("jobsId") Integer jobsId);
}
