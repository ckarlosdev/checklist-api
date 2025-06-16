package com.ck.wi.service.googleChecklist;

import com.ck.wi.model.entity.googleChecklist.EquipmentsGoogleChecklist;

import java.time.LocalDate;

public interface IEquipmentsGoogleChecklist {
    EquipmentsGoogleChecklist findById(Integer id);

    EquipmentsGoogleChecklist findByJobsIdAndDate(Integer jobsId, LocalDate date);

}
