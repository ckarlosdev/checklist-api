package com.ck.wi.model.entity.hazard;

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
@Table(name = "activities")
public class Activity implements Serializable {

    @Id
    @Column(name = "activities_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activitiesId;

    @ManyToOne
    @JoinColumn(name = "pre_tasks_id")
    private PreTask preTask;

    @Column(name = "activity")
    private String activity;

    @Column(name = "hazards")
    private String hazards;

    @Column(name = "controls")
    private String controls;

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
