package com.ck.wi.model.dto.hazard.create;


import java.time.LocalDate;


public interface PretaskViewDto {
    Integer getPreTasksId();
    Integer getJobsId();
    LocalDate getDate();
    String getSupervisor();
    String getComment();
}
