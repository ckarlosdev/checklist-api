package com.ck.wi.model.dto.hazard.create;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class PreTaskCreateDto implements Serializable {
    private Integer preTasksId;
    private Integer jobsId;
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String supervisor;
    private String comment;
    private List<ActivityCreateDto> activities;
    private List<PreTaskOptionCreateDto> options;
    private List<PtSignatureDto> signatures;
}
