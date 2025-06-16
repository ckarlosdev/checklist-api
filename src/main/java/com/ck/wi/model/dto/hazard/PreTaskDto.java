package com.ck.wi.model.dto.hazard;

import com.ck.wi.model.entity.hazard.PretasksOption;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PreTaskDto implements Serializable {

    private Integer preTasksId;
    private Integer jobsId;
    private LocalDate date;
    private String supervisor;
    private String comment;
    private List<ActivityDto> activities;
    private List<PretasksOptionDto> pretasksOptionDtos;
//    private String createdBy;
//    private Date createdDate;
//    private String updatedBy;
//    private Date updatedDate;
//    private String status;
//    private String token;

}
