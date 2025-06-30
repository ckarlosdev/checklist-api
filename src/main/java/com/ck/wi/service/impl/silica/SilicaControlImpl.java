package com.ck.wi.service.impl.silica;

import com.ck.wi.model.dao.silica.SilicaControlDao;
import com.ck.wi.model.entity.silica.SilicaControl;
import com.ck.wi.service.silica.ISilicaControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SilicaControlImpl implements ISilicaControl {

    @Autowired
    private SilicaControlDao silicaControlDao;

    @Override
    public SilicaControl findById(Integer silicaControlsId){
        return silicaControlDao.findById(silicaControlsId).orElse(null);
    }
}
