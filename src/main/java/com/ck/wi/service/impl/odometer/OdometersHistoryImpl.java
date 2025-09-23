package com.ck.wi.service.impl.odometer;

import com.ck.wi.model.dao.odometer.OdometersHistoryDao;
import com.ck.wi.model.dto.odometer.OdometersHistoryDto;
import com.ck.wi.model.entity.odometer.OdometersHistory;
import com.ck.wi.service.odometer.IOdometersHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OdometersHistoryImpl implements IOdometersHistory {

    @Autowired
    private OdometersHistoryDao odometersHistoryDao;

    @Transactional
    @Override
    public OdometersHistory save(OdometersHistory odometersHistory){
        return odometersHistoryDao.save(odometersHistory);
    }

    @Transactional(readOnly = true)
    @Override
    public OdometersHistory findById(Integer id){
        return odometersHistoryDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OdometersHistory> findByOdometersId(Integer odometersId){
        return (List<OdometersHistory>) odometersHistoryDao.findByOdometersId(odometersId);
    }
}
