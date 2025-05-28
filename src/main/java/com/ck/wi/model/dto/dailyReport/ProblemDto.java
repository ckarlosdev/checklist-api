package com.ck.wi.model.dto.dailyReport;

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
    private Integer drEquipmentsId;
    private String type;
    private String priority;
    private String description;
    private String status;
}
