package com.ck.wi.model.dao.demoChecklist;

import com.ck.wi.model.entity.demoChecklist.DemoChecklist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoChecklistDao extends CrudRepository<DemoChecklist, Integer> {
}
