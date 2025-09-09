package com.ck.wi.model.dao.demoChecklist;

import com.ck.wi.model.entity.demoChecklist.DemoChecklistsItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoChecklistsItemDao extends CrudRepository<DemoChecklistsItem, Integer> {
}
