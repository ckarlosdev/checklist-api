package com.ck.wi.model.entity.assignment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "assignment")
@Builder
@Entity
@Table(name = "assignments_absences")
public class AssignmentAbsence implements Serializable {
    // assignments_absences_id, assignments_id, employees_id, absence_type, comments, created_by, created_date, updated_by, updated_date

    @Id
    @Column(name = "assignments_absences_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assignmentsAbsencesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignments_id", nullable = false)
    @JsonBackReference
    private Assignment assignment;

    @Column(name = "employees_id")
    private Integer employeesId;

    @Column(name = "absence_type")
    private String absenceType;

    @Column(name = "comments")
    private String comments;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "absence_status")
    private String absenceStatus;
}
