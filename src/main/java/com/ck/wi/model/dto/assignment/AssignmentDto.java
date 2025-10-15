package com.ck.wi.model.dto.assignment;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssignmentDto implements Serializable {
    private Integer assignmentsId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String createdBy;
    private List<AssignmentJobDto> assignmentJobDtos = new ArrayList<>();
}
