package com.ck.wi.model.dto.assignment;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AbsenceDto implements Serializable {
    private Integer assignmentsAbsencesId;
    private Integer assignmentsId;
    private Integer employeesId;
    private String absenceType;
    private String comments;
}
