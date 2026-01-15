package com.ck.wi.model.dao.hazard;

import com.ck.wi.model.entity.hazard.PtSignature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PtSignaruteDao extends CrudRepository<PtSignature, Integer> {
    List<PtSignature> findByPreTasksIdAndPtSignaturesStatus(Integer id, String status);
}
