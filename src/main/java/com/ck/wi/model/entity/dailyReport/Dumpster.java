package com.ck.wi.model.entity.dailyReport;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "dumpsters")
public class Dumpster implements Serializable {
    @Id
    @Column(name = "dr_dumpsters_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer drDumpstersId;

    @Column(name = "daily_report_id")
    private Integer dailyReportId;

    @Column(name = "concret40")
    private Integer concret40;

    @Column(name = "concret35")
    private Integer concret35;

    @Column(name = "concret30")
    private Integer concret30;

    @Column(name = "concret20")
    private Integer concret20;

    @Column(name = "concret12")
    private Integer concret12;

    @Column(name = "metal40")
    private Integer metal40;

    @Column(name = "metal35")
    private Integer metal35;

    @Column(name = "metal30")
    private Integer metal30;

    @Column(name = "metal20")
    private Integer metal20;

    @Column(name = "metal12")
    private Integer metal12;

    @Column(name = "cd40")
    private Integer cd40;

    @Column(name = "cd35")
    private Integer cd35;

    @Column(name = "cd30")
    private Integer cd30;

    @Column(name = "cd20")
    private Integer cd20;

    @Column(name = "cd12")
    private Integer cd12;

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

    @Column(name = "concretquad")
    private Integer concretQuad;

    @Column(name = "concretsemi")
    private Integer concretSemi;

    @Column(name = "concretgondola")
    private Integer concretGondola;

    @Column(name = "metalquad")
    private Integer metalQuad;

    @Column(name = "metalsemi")
    private Integer metalSemi;

    @Column(name = "metalgondola")
    private Integer metalGondola;

    @Column(name = "cdquad")
    private Integer cdQuad;

    @Column(name = "cdsemi")
    private Integer cdSemi;

    @Column(name = "cdgondola")
    private Integer cdGondola;
}
