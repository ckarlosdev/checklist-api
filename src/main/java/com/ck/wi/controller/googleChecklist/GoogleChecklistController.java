package com.ck.wi.controller.googleChecklist;

import com.ck.wi.model.dto.googleChecklist.GoogleChecklistDto;
import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.googleChecklist.EquipmentsGoogleChecklist;
import com.ck.wi.model.entity.googleChecklist.GoogleChecklist;
import com.ck.wi.service.IJob;
import com.ck.wi.service.googleChecklist.IEquipmentsGoogleChecklist;
import com.ck.wi.service.googleChecklist.IGoogleChecklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io/Dashboard"
})
@RestController
@RequestMapping("/api/v1")
public class GoogleChecklistController {

    @Autowired
    private IGoogleChecklist googleChecklistService;

    @Autowired
    private IJob jobService;

    @Autowired
    private IEquipmentsGoogleChecklist equipmentsGoogleChecklistService;

    @GetMapping("cl/{number}/by-date")
    public List<GoogleChecklistDto> getGoogleChecklistByJobNumberAndDate(
            @PathVariable String number,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){

        Job job = jobService.findByNumber(number);

        EquipmentsGoogleChecklist equipmentsGoogleChecklist = equipmentsGoogleChecklistService.findByJobsIdAndDate(job.getJobsId(), date);

        List<GoogleChecklist> googleChecklists = googleChecklistService.findByEquipmentsGoogleChecklistsId(equipmentsGoogleChecklist.getEquipmentsGoogleChecklistsId());

        return googleChecklists.stream()
                .map(googleChecklist ->
                        GoogleChecklistDto.builder()
                                .googleChecklistsId(googleChecklist.getGoogleChecklistsId())
                                .equipmentsGoogleChecklistsId(googleChecklist.getEquipmentsGoogleChecklistsId())
                                .equipmentNumber(googleChecklist.getEquipmentNumber())
                                .equipmentName(googleChecklist.getEquipmentName())
                                .operator(googleChecklist.getOperator())
                                .odometer(googleChecklist.getOdometer())
                                .oil(googleChecklist.getOil())
                                .hydraulic(googleChecklist.getHydraulic())
                                .filter(googleChecklist.getFilter())
                                .radiator(googleChecklist.getRadiator())
                                .track(googleChecklist.getTrack())
                                .attachment(googleChecklist.getAttachment())
                                .leaking(googleChecklist.getLeaking())
                                .diesel(googleChecklist.getDiesel())
                                .clean(googleChecklist.getClean())
                                .comment(googleChecklist.getComment())
                                .createdBy(googleChecklist.getCreatedBy())
                                .createdDate(googleChecklist.getCreatedDate())
                                .updatedBy(googleChecklist.getUpdatedBy())
                                .updatedDate(googleChecklist.getUpdatedDate())
                                .status(googleChecklist.getStatus())
                                .otherType(googleChecklist.getOtherType())
                                .build())
                .collect(Collectors.toList());
    }


}
