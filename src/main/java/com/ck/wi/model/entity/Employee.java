package com.ck.wi.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "employees")
public class Employee implements Serializable {
    //    employees_id, legal_name, pay_group, employee_number, first_name, last_name, status, department, title

    @Id
    @Column(name = "employees_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeesId;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "pay_group")
    private String payGroup;

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "status")
    private String status;

    @Column(name = "department")
    private String department;

    @Column(name = "title")
    private String title;

    @Column(name = "employee_status")
    private String employeeStatus;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date", insertable = false, updatable = false)
    private String createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date", insertable = false)
    @UpdateTimestamp
    private String updatedDate;

}
