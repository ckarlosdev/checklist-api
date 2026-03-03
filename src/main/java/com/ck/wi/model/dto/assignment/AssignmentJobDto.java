package com.ck.wi.model.dto.assignment;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssignmentJobDto implements Serializable {
    private Integer jobsId;
    private String startTime;
    private String assignmentComment;
    private List<AssignmentEmployeeDto> assignmentEmployeeDtos = new ArrayList<>();
}
