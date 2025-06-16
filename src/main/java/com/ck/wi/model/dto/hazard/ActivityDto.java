package com.ck.wi.model.dto.hazard;

import com.ck.wi.model.entity.hazard.PreTask;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ActivityDto implements Serializable {

    private Integer activitiesId;
    private String activity;
    private String hazards;
    private String controls;
//    private String createdBy;
//    private Date createdDate;
//    private String updatedBy;
//    private Date updatedDate;
//    private String status;
}
