package com.ck.wi.model.dto.silica;

import com.ck.wi.model.entity.Employee;
import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.silica.SilicaControl;
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
public class SilicaDto implements Serializable {
    private Integer silicaId;
    private Job job;
    private Employee employee;
    private LocalDate eventDate;
    private String workDescription;
//    private String diagramId;
//    private String diagramFolder;
    private String ventilationArea;
    private LocalDate datePlan;
    private String equipmentDescription;
    private String signatureId;
    private String signatureFolder;
    private List<SilicaControlDto> silicaControls;
    private String diagramData;
    private String createdBy;
    private String updatedBy;
}
