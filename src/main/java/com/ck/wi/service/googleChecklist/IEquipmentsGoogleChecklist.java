package com.ck.wi.service.googleChecklist;

import com.ck.wi.model.dto.googleChecklist.EquipmentsGoogleChecklistCreateDto;
import com.ck.wi.model.entity.googleChecklist.EquipmentsGoogleChecklist;

import java.time.LocalDate;
import java.util.List;

public interface IEquipmentsGoogleChecklist {
    EquipmentsGoogleChecklist findById(Integer id);

    EquipmentsGoogleChecklist findByJobsIdAndDate(Integer jobsId, LocalDate date);

    Integer save(EquipmentsGoogleChecklistCreateDto checklistCreateDto);

    void update(EquipmentsGoogleChecklistCreateDto checklistCreateDto);

    List<EquipmentsGoogleChecklist> getAllChecklist(Integer jobId);

}
