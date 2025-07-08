package com.ck.wi.model.dto.silica;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SilicaCreateDto implements Serializable {
    private Integer silicaId;
    private Integer jobsId;
    private Integer employeesId;
    private LocalDate eventDate;
    private String workDescription;
//    private String diagramId;
//    private String diagramFolder;
    private String ventilationArea;
    private Date datePlan;
    private String equipmentDescription;
    private String signatureId;
    private String signatureFolder;
    private List<SilicaControlCreateDto> silicaControls;
    private String diagramData;
    private String createdBy;
    private String updatedBy;
}
