package com.ck.wi.service.impl.googleChecklist;

import com.ck.wi.model.dao.googleChecklist.GoogleChecklistDao;
import com.ck.wi.model.dto.googleChecklist.EquipmentsGoogleChecklistCreateDto;
import com.ck.wi.model.entity.googleChecklist.GoogleChecklist;
import com.ck.wi.service.googleChecklist.IGoogleChecklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoogleChecklistImpl implements IGoogleChecklist {

    @Autowired
    private GoogleChecklistDao googleChecklistDao;

    @Override
    public List<GoogleChecklist> findByEquipmentsGoogleChecklistsId(Integer equipmentsGoogleChecklistsId){
        return googleChecklistDao.findByEquipmentsGoogleChecklistsId(equipmentsGoogleChecklistsId);
    }


}
