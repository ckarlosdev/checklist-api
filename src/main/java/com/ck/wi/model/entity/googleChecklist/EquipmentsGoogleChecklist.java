package com.ck.wi.model.entity.googleChecklist;

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
@Table(name = "equipments_google_checklists")
public class EquipmentsGoogleChecklist implements Serializable {

    @Id
    @Column(name = "equipments_google_checklists_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipmentsGoogleChecklistsId;

    @Column(name = "jobs_id")
    private Integer jobsId;

    @Column(name = "total")
    private Integer total;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "date")
    private String date;

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

    @Column(name = "token")
    private String token;
}
