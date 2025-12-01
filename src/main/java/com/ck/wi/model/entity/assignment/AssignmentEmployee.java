package com.ck.wi.model.entity.assignment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "assignments_employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // solo la PK
@ToString(exclude = "assignmentJob") // evita recursión infinita
public class AssignmentEmployee implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "assignments_employees_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assignmentsEmployeesId;

    @Column(name = "employees_id")
    private Integer employeesId;

    @Column(name = "ae_status")
    private String aeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_job_id", nullable = false)
    @JsonBackReference
    private AssignmentJob assignmentJob;
}
