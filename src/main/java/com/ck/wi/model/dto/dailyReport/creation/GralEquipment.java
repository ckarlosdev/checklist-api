package com.ck.wi.model.dto.dailyReport.creation;

import lombok.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GralEquipment {
    private Integer equipmentId;
    private String number;
    private String name;
    private String serialNumber;
}
