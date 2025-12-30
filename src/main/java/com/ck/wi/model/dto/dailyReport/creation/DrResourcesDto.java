package com.ck.wi.model.dto.dailyReport.creation;

import com.ck.wi.model.entity.dailyReport.DrEmployee;
import com.ck.wi.model.entity.dailyReport.DrEquipment;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DrResourcesDto implements Serializable {
    private List<DrEmployeeCreateDto> employees;
    private List<DrEquipmentCreateDto> equipments;
    private List<DrRentalCreateDto> rentals;
    private List<DrToolCreateDto> tools;
}