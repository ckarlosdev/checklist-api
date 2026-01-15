package com.ck.wi.service.hazard;

import com.ck.wi.model.dto.hazard.PreTaskDto;
import com.ck.wi.model.dto.hazard.create.PreTaskCreateDto;
import com.ck.wi.model.dto.hazard.create.PretaskViewDto;
import com.ck.wi.model.entity.hazard.PreTask;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPreTask {
    Integer savePretask(PreTaskCreateDto preTaskCreateDto);

    void updatePretask(PreTaskCreateDto preTaskCreateDto);

    PreTaskCreateDto getReportById(Integer pretaskId);

    List<PretaskViewDto> getPretasksByJobId(Integer jobId);

    PreTask findById(Integer pretaskId);

    PreTask findByJobsIdAndDate(Integer jobId, LocalDate date);

    Optional<PreTaskDto> getPretaskWithActivities(Integer id);
}
