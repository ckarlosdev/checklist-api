package com.ck.wi.model.entity.assignment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // solo usa el ID
@ToString(exclude = "assignmentJobs")
public class Assignment implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "assignments_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assignmentsId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "assignment_status")
    private String assignmentStatus;

//    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    @Builder.Default
    private Set<AssignmentJob> assignmentJobs = new HashSet<>();

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    @Builder.Default
    private Set<AssignmentAbsence> assignmentAbsences = new HashSet<>();

    public void addAbsence(AssignmentAbsence absence) {
        assignmentAbsences.add(absence);
        absence.setAssignment(this);
    }

    public void removeAbsence(AssignmentAbsence absence) {
        assignmentAbsences.remove(absence);
        absence.setAssignment(null);
    }
}
