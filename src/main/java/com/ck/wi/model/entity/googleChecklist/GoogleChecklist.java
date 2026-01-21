package com.ck.wi.model.entity.googleChecklist;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "google_checklists")
public class GoogleChecklist implements Serializable {

    @Id
    @Column(name = "google_checklists_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer googleChecklistsId;

    @Column(name = "equipments_google_checklists_id")
    private Integer equipmentsGoogleChecklistsId;

    @Column(name = "equipment_number")
    private String equipmentNumber;

    @Column(name = "equipment_name")
    private String equipmentName;

    @Column(name = "operator")
    private String operator;

    @Column(name = "odometer")
    private double odometer;

    @Column(name = "oil")
    private String oil;

    @Column(name = "hydraulic")
    private String hydraulic;

    @Column(name = "filter")
    private String filter;

    @Column(name = "radiator")
    private String radiator;

    @Column(name = "track")
    private String track;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "leaking")
    private String leaking;

    @Column(name = "diesel")
    private String diesel;

    @Column(name = "clean")
    private String clean;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "other_type")
    private String otherType;
}
