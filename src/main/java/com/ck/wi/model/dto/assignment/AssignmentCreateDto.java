package com.ck.wi.model.dto.assignment;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssignmentCreateDto {
    private Integer assignmentId;
    private String createdBy;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<AssignmentJobCreateDto> assignmentJobCreateDtoList;
    private List<AbsenceCreateDto> absenceCreateDtoList;
}
