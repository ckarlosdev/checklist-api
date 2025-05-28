package com.ck.wi.model.dto.dailyReport;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PhotoDto implements Serializable {
    private Integer photosId;
    private Integer dailyReportId;
    private Date drDate;
    private String pathId;
    private String folderId;
    private String name;
    private String type;
    private String status;
}
