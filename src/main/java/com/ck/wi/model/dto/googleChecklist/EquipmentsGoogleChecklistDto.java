package com.ck.wi.model.dto.googleChecklist;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EquipmentsGoogleChecklistDto implements Serializable {

    private Integer equipmentsGoogleChecklistsId;
    private Integer jobsId;
    private Integer total;
    private String jobName;
    private Date date;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String status;
    private String token;
}
