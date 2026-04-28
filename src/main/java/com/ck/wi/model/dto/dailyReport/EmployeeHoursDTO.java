package com.ck.wi.model.dto.dailyReport;

import lombok.*;

import java.io.Serializable;


public interface EmployeeHoursDTO {
    Long getEmployeesId();
    String getName();
    Double getTotalHrs();
}
