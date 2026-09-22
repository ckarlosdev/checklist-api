package com.ck.wi.model.dto.dailyReport.dashboard;

public interface DailyReportDetailProjection {
    Long getJobId();
    String getJobNumber();
    String getJobName();
    Long getDailyReportId();
    String getForeman();
    String getReportDate();
    Long getDrEmployeesId();
    String getEmployeeName();
    String getEmployeeTitle();
    String getInHour();
    String getOutHour();
    String getLunch();
    Double getHoursWorked();
}
