package com.ck.wi.model.dao.demoChecklist;

import com.ck.wi.model.entity.demoChecklist.DemoItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoItemDao extends CrudRepository<DemoItem, Integer> {
}
