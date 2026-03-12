package com.ck.wi.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EquipmentDto implements Serializable {
    private Integer equipmentsId;
    private String family;
    private String number;
    private String name;
    private String manufacturing;
    private String model;
    private String year;
    private String purchaseDate;
    private String status;
    private String condition;
    private String serialNumber;
    private float hour;
    private String user;
}
