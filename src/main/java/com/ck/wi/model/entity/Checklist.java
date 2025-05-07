package com.ck.wi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "checklists")
public class Checklist implements Serializable {
    // checklists_id, equipments_id, employees_id, odometer, oil, hydraulic, filter, radiator, track, attachment, leaking, diesel, clean, comment, created_by, created_date, updated_by, updated_date, type,

    @Id
    @Column(name = "checklists_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer checklistsId;

    @ManyToOne
    @JoinColumn(name = "equipments_id")
    @JsonBackReference
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "employees_id")
    @JsonBackReference
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "jobs_id")
    @JsonBackReference
    private Job job;

    @Column(name = "type")
    private String type;

    @Column(name = "date")
    private Date date;

    @Column(name = "odometer")
    private String odometer;

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
    private Date createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "status")
    private String status;
}
