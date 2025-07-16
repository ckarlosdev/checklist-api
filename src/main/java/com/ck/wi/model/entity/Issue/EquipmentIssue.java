package com.ck.wi.model.entity.Issue;

import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Equipment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "equipments_issues")
public class EquipmentIssue implements Serializable {

    @Id
    @Column(name = "equipments_issues_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipmentsIssuesId;

    @ManyToOne
    @JoinColumn(name = "checklists_id")
    @JsonBackReference
    private Checklist checklist;

    @ManyToOne
    @JoinColumn(name = "equipments_id")
    @JsonBackReference
    private Equipment equipment;

    @Column(name = "flow")
    private String flow;

    @Column(name = "reported_by")
    private String reportedBy;

    @Column(name = "reported_date")
    private LocalDate reportedDate;

    @Column(name = "priority_issue")
    private String priorityIssue;

    @Column(name = "type_issue")
    private String typeIssue;

    @Column(name = "description_issue")
    private String descriptionIssue;

    @Column(name = "details")
    private String details;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "issue_status")
    private String issueStatus;
}
