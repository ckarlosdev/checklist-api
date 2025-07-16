package com.ck.wi.model.entity.Issue;

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
@Table(name = "issues_history")
public class IssuesHistory implements Serializable {

    @Id
    @Column(name = "issues_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer issuesHistoryId;

    @ManyToOne
    @JoinColumn(name = "equipments_issues_id")
    @JsonBackReference
    private EquipmentIssue equipmentIssue;

    @Column(name = "last_flow")
    private String lastFlow;

    @Column(name = "new_flow")
    private String newFlow;

    @Column(name = "comments")
    private String comments;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "history_status")
    private String historyStatus;
}
