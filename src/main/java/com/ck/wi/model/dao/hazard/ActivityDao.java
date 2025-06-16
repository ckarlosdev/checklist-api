package com.ck.wi.model.dao.hazard;

import com.ck.wi.model.entity.hazard.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDao extends CrudRepository<Activity, Integer> {

}

