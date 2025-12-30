package com.ck.wi.model.dto.dailyReport.creation;

import com.ck.wi.model.entity.dailyReport.DrRental;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DailyReportCreateDto implements Serializable {
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
}
