package com.ck.wi.model.dto.dailyReport.dashboard;

public interface EmployeeSummaryProjection {
    Long getJobId();
    String getJobNumber();
    String getJobName();
    String getEmployeeTitle();
    String getEmployeeName();
    Long getTotalReports();
    Long getTotalEmployeeEntries();
    Double getTotalHours();
}

