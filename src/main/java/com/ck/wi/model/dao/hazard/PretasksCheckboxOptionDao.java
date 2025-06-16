package com.ck.wi.model.dao.hazard;

import com.ck.wi.model.entity.hazard.PretasksCheckboxOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PretasksCheckboxOptionDao extends CrudRepository<PretasksCheckboxOption, Integer> {
}
