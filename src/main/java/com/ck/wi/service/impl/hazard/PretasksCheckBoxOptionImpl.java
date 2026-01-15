package com.ck.wi.service.impl.hazard;

import com.ck.wi.model.dao.hazard.PretasksCheckboxOptionDao;
import com.ck.wi.model.entity.hazard.PretasksCheckboxOption;
import com.ck.wi.service.hazard.IPretasksCheckboxOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PretasksCheckBoxOptionImpl implements IPretasksCheckboxOption {

    @Autowired
    private PretasksCheckboxOptionDao pretasksCheckboxOptionDao;

    @Transactional
    @Override
    public List<PretasksCheckboxOption> getAll(){
        return (List<PretasksCheckboxOption>) pretasksCheckboxOptionDao.findAll();
    }
}
