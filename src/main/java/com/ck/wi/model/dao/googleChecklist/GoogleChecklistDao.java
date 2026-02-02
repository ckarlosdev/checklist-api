package com.ck.wi.model.dao.googleChecklist;

import com.ck.wi.model.entity.googleChecklist.GoogleChecklist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoogleChecklistDao extends CrudRepository<GoogleChecklist, Integer> {
    List<GoogleChecklist> findByEquipmentsGoogleChecklistsId(Integer equipmentsGoogleChecklistsId);
    List<GoogleChecklist> findByEquipmentsGoogleChecklistsIdAndStatus(Integer equipmentsGoogleChecklistsId, String status);
}
