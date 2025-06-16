package com.ck.wi.service.hazard;

import com.ck.wi.model.dto.hazard.PreTaskDto;
import com.ck.wi.model.entity.hazard.PreTask;

import java.time.LocalDate;
import java.util.Optional;

public interface IPreTask {
    PreTask findById(Integer pretaskId);

    PreTask findByJobsIdAndDate(Integer jobId, LocalDate date);

    Optional<PreTaskDto> getPretaskWithActivities(Integer id);
}
