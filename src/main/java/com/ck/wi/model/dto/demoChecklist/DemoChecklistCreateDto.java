package com.ck.wi.model.dto.demoChecklist;

import com.ck.wi.model.entity.Job;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DemoChecklistCreateDto implements Serializable {
    private Integer demoChecklistsId;
    private Integer jobsId;
    private LocalDate checklistDate;
    private String buildingType;
    private String foreman;
    private String notes;
    private String signature;
    private String permits;
    private List<DemoChecklistsItemDto> items;
    private String createdBy;
    private String updatedBy;
}
