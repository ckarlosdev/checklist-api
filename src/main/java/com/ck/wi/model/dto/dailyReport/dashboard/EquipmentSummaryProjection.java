package com.ck.wi.model.dto.dailyReport.dashboard;

public interface EquipmentSummaryProjection {
    Long getJobId();
    String getJobNumber();
    String getEquipmentName();
    Long getTotalReports();
    Long getTotalEquipmentEntries();
    Double getTotalHours();
}
