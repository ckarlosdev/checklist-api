package com.ck.wi.model.dto.dailyReport.creation;

import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DrEmployeeCreateDto implements Serializable {
    private Integer drEmployeesId;
    private Integer employeesId;
    private LocalTime inHour;
    private LocalTime outHour;
    private String lunch;
    private String ppe;
    private String comment;
}
