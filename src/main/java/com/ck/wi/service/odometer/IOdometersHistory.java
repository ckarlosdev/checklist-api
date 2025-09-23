package com.ck.wi.service.odometer;

import com.ck.wi.model.dto.odometer.OdometersHistoryDto;
import com.ck.wi.model.entity.odometer.OdometersHistory;

import java.util.List;

public interface IOdometersHistory {

    OdometersHistory save(OdometersHistory odometersHistory);

    OdometersHistory findById(Integer id);

    List<OdometersHistory> findByOdometersId(Integer odometersId);

}
