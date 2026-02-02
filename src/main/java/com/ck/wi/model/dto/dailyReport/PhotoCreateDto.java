package com.ck.wi.model.dto.dailyReport;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PhotoCreateDto implements Serializable {
    private Integer photosId;
    private LocalDate drDate;
    private String pathId;
    private String folderId;
    private String name;
    private String type;
    private String userName;
}
