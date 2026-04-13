package com.ck.wi.model.dto.assignment;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AbsenceCreateDto {
    private Integer assignmentsAbsencesId;
    private Integer assignmentsId;
    private Integer employeesId;
    private String absenceType;
    private String comments;
}
