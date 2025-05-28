package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.Problem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProblemDao extends CrudRepository<Problem, Integer> {
    List<Problem> findByDrEquipmentsIdAndStatus(Integer drEquipmentsId, String status);
}
