package com.ck.wi.model.entity.dailyReport;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "dr_dumpsters")
public class DrDumpster implements Serializable {

    @Id
    @Column(name = "dr_dumpsters_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer drDumpstersId;

    @Column(name = "daily_report_id")
    private Integer dailyReportId;

    @Column(name = "source_dumpster")
    private String sourceDumpster;

    @Column(name = "size_dumpster")
    private String sizeDumpster;

    @Column(name = "type_dumpster")
    private String typeDumpster;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "dumpsters_status")
    private String dumpstersStatus;
}
