package com.ck.wi.model.dto.hazard.create;

import com.ck.wi.model.entity.hazard.PreTask;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ActivityCreateDto implements Serializable {
    private Integer activitiesId;
    private String activity;
    private String hazards;
    private String controls;
}
