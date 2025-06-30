package com.ck.wi.service.impl.silica;

import com.ck.wi.model.dao.silica.ControlDescriptionDao;
import com.ck.wi.model.entity.silica.Control;
import com.ck.wi.model.entity.silica.ControlsDescription;
import com.ck.wi.service.silica.IControlsDescription;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ControlDescriptionImpl implements IControlsDescription {

    @Autowired
    private ControlDescriptionDao controlDescriptionDao;

    @Transactional
    @Override
    public Optional<ControlsDescription> findById(Integer id){
        return controlDescriptionDao.findById(id);
    }

    @Transactional
    @Override
    public List<ControlsDescription> findByControl(Control control){
        return controlDescriptionDao.findByControl(control);
    }
}
