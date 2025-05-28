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
@Table(name = "tools")
public class Tool implements Serializable {
    @Id
    @Column(name = "dr_tool_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer drToolId;

    @Column(name = "daily_report_id")
    private Integer dailyReportId;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "name")
    private String name;

    @Column(name = "other")
    private String other;

    @Column(name = "type")
    private String type;

    @Column(name = "comments")
    private String comments;

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
