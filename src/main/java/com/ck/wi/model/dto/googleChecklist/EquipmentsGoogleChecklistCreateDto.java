package com.ck.wi.model.dto.googleChecklist;

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
public class EquipmentsGoogleChecklistCreateDto implements Serializable {

    private Integer equipmentsGoogleChecklistsId;
    private Integer jobsId;
    private String userName;
    private LocalDate date;
    private List<GoogleCheckListCreateDto> checklists;
}
