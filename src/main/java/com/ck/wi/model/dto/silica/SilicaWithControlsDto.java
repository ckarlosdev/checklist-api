package com.ck.wi.model.dto.silica;

import com.ck.wi.model.entity.Employee;
import com.ck.wi.model.entity.Job;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SilicaWithControlsDto implements Serializable {
    private Integer silicaId;
    private Job job;
    private Employee employee;
    private Date event_date;
    private String workDescription;
//    private String diagramId;
//    private String diagramFolder;
    private String ventilationArea;
    private Date datePlan;
    private String equipmentDescription;
    private String signatureId;
    private String signatureFolder;
    private List<SilicaControlDto> assignedControls;
    private String diagramData;
}
