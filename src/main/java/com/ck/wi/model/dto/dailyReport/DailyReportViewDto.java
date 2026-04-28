package com.ck.wi.model.dto.dailyReport;

import com.ck.wi.model.dto.dailyReport.creation.*;

import java.time.LocalDate;
import java.util.List;

public class DailyReportViewDto {
    private Integer dailyReportId;
    private String foreman;
    private String userName;
    private LocalDate date;
    private String description;
    private String manOther;
    private String equipmentOther;
    private String issues;
    private List<DrEmployeeCreateDto> employees;
    private List<DrEquipmentCreateDto> equipments;
    private List<DrRentalCreateDto> rentals;
    private List<DrToolCreateDto> tools;
    private List<DrDumpsterCreateDto> dumpsters;
    private List<PhotoCreateDto> photos;
}
