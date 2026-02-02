package com.ck.wi.model.entity.dailyReport;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "photos")
public class Photo implements Serializable {
    @Id
    @Column(name = "photos_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photosId;

    @Column(name = "daily_report_id")
    private Integer dailyReportId;

    @Column(name = "dr_date")
    private LocalDate drDate;

    @Column(name = "path_id")
    private String pathId;

    @Column(name = "folder_id")
    private String folderId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "status")
    private String status;
}
