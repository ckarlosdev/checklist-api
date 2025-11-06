package com.ck.wi.model.dto.dailyReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DailyReportGralDto {
    private int dailyReportId;
    private Date date;
    private String foreman;
    private String manTotal;
    private String equipmentTotal;
    private int photosTotal;
    private int toolsTotal;
    private int dumpstersCount;
}
