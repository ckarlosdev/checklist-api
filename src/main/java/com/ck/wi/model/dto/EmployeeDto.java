package com.ck.wi.model.dto;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmployeeDto implements Serializable {

    private Integer employeesId;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String status;
    private String title;
}
