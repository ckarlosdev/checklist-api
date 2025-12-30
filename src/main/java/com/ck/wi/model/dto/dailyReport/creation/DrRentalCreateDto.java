package com.ck.wi.model.dto.dailyReport.creation;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DrRentalCreateDto implements Serializable {
    private Integer drRentalsId;
    private Integer employeesId;
    private String equipmentType;
    private String equipmentName;
    private String company;
    private String equipmentNumber;
    private Double odometer;
}
