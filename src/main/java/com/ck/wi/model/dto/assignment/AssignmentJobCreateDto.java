package com.ck.wi.model.dto.assignment;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssignmentJobCreateDto {
    private Integer id;
    private String title;
    private String number;
    private Integer[] assignedEmployeeIds;
}
