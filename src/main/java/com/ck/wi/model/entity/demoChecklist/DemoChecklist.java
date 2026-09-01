package com.ck.wi.model.entity.demoChecklist;

import com.ck.wi.model.entity.Job;
import com.ck.wi.service.impl.demoChecklist.DemoChecklisImpl;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "demo_checklists")
public class DemoChecklist implements Serializable {

    @Id
    @Column(name = "demo_checklists_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer demoChecklistsId;

    @ManyToOne
    @JoinColumn(name = "jobs_id")
    @JsonBackReference
    private Job job;

    @Column(name = "checklist_date")
    private LocalDate checklistDate;

    @Column(name = "building_type")
    private String buildingType;

    @Column(name = "foreman")
    private String foreman;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "signature")
    private String signature;

    @Column(name = "permits")
    private String permits;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @Column(name = "demo_checklists_status")
    private String demoChecklistsStatus;

    @OneToMany(mappedBy = "demoChecklist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DemoChecklistsItem> items;

}
