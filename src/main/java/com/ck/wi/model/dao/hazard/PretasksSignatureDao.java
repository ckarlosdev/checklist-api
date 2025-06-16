package com.ck.wi.model.dao.hazard;

import com.ck.wi.model.entity.hazard.PretasksSignature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PretasksSignatureDao extends CrudRepository<PretasksSignature, Integer> {
}
