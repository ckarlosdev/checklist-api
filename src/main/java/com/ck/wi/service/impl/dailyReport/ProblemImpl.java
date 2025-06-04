package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.dailyReport.ProblemDao;
import com.ck.wi.model.entity.dailyReport.Problem;
import com.ck.wi.service.dailyReport.IProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemImpl implements IProblem {

    @Autowired
    private ProblemDao problemDao;

    @Override
    public List<Problem> findByDrEquipmentsId(Integer drEquipmentId){
        return (List<Problem>) problemDao.findByDrEquipmentsIdAndStatus(drEquipmentId, "1");
    }
    @Override
    public List<Problem> findByDrEquipmentsIds(List<Integer> drEquipmentIds){
        return (List<Problem>) problemDao.findByDrEquipmentsIdIn(drEquipmentIds);
    }

}
