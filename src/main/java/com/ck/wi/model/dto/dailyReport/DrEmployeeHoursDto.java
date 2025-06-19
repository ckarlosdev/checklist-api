package com.ck.wi.model.dto.dailyReport;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Time;

@Data
@NoArgsConstructor
@ToString
public class DrEmployeeHoursDto {
    private Integer drEmployeesId;
    private Time outHour;
    private Time inHour;
    private Time timeDifference;
    private BigDecimal totalHoursDecimal;
    private String lunch;

    public DrEmployeeHoursDto(
            Integer drEmployeesId,
            Time outHour,
            Time inHour,
            Time timeDifference,
            BigDecimal totalHoursDecimal,
            String lunch
    ) {
        this.drEmployeesId = drEmployeesId;
        this.outHour = outHour;
        this.inHour = inHour;
        this.timeDifference = timeDifference;
        this.totalHoursDecimal = totalHoursDecimal;
        this.lunch = lunch;
    }
}
