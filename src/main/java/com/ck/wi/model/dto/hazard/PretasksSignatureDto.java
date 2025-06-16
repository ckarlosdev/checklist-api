package com.ck.wi.model.dto.hazard;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PretasksSignatureDto implements Serializable {

    private Integer pretasksSignaturesId;
    private Integer preTasksId;
    private String date;
    private String pathId;
    private String folderId;
    private String name;
    private String employee;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String status;
}
