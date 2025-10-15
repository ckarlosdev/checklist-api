package com.ck.wi.model.dto.assignment;

import com.ck.wi.model.dto.EmployeeDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssignmentEmployeeDto implements Serializable {
    private Integer assignmentsEmployeesId;
    private Integer employeesId;
}
