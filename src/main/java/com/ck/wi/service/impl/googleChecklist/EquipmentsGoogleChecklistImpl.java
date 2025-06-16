package com.ck.wi.service.impl.googleChecklist;

import com.ck.wi.model.dao.googleChecklist.EquipmentsGoogleChecklistDao;
import com.ck.wi.model.entity.googleChecklist.EquipmentsGoogleChecklist;
import com.ck.wi.service.googleChecklist.IEquipmentsGoogleChecklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EquipmentsGoogleChecklistImpl implements IEquipmentsGoogleChecklist {

    @Autowired
    private EquipmentsGoogleChecklistDao equipmentsGoogleChecklistDao;

    @Override
    public EquipmentsGoogleChecklist findById(Integer id){
        return equipmentsGoogleChecklistDao.findById(id).orElse(null);
    }

    @Override
    public EquipmentsGoogleChecklist findByJobsIdAndDate(Integer jobsId, LocalDate date){
        return equipmentsGoogleChecklistDao.findByJobsIdAndDate(jobsId, date);
    }
}
