package com.ck.wi.model.entity.Issue;

import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Equipment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "equipment")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "issue_reports")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE issue_reports SET deleted_at = NOW() WHERE issue_reports_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class IssueReport {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "issue_reports_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipments_id", nullable = false)
    @JsonBackReference
    private Equipment equipment;

    @Column(name = "reported_by")
    private String reportedBy;

    @Column(name = "priority_issue")
    private String priorityIssue;

    @Column(name = "type_issue")
    private String typeIssue;

    @Column(name = "description_issue")
    private String descriptionIssue;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
