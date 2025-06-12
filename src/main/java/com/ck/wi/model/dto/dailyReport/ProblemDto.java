package com.ck.wi.model.dto.dailyReport;

import com.ck.wi.model.entity.dailyReport.DrEquipment;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProblemDto implements Serializable {
    private Integer problemsId;
    private DrEquipment drEquipment;
    private String type;
    private String priority;
    private String description;
    private String status;
}
