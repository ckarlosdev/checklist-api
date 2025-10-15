package com.ck.wi.model.entity.assignment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "assignment_job")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // solo la PK
@ToString(exclude = {"assignment", "assignmentEmployees"})
public class AssignmentJob implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "assignment_job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assignmentJobId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignments_id")
    @JsonBackReference
    private Assignment assignment;

//    @Column(name = "assignments_id")
//    private Integer assignmentsId;

    @Column(name = "jobs_id")
    private Integer jobsId;

    @Column(name = "aj_status")
    private String ajStatus;

    @OneToMany(mappedBy = "assignmentJob", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<AssignmentEmployee> assignmentEmployees = new HashSet<>();
}
