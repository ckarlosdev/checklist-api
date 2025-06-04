package com.ck.wi.service.dailyReport;

import com.ck.wi.model.entity.dailyReport.Problem;

import java.util.List;

public interface IProblem {
    List<Problem> findByDrEquipmentsId(Integer drEquipmentsId);

    List<Problem> findByDrEquipmentsIds(List<Integer> drEquipmentsIds);
}
