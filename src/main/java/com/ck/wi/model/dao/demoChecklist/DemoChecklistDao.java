package com.ck.wi.model.dao.demoChecklist;

import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.demoChecklist.DemoChecklist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemoChecklistDao extends CrudRepository<DemoChecklist, Integer> {
    List<DemoChecklist> findByJobAndDemoChecklistsStatusOrderByChecklistDateDesc(Job job, String status);
}
