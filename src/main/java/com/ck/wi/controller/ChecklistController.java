package com.ck.wi.controller;

import com.ck.wi.model.dto.ChecklistDto;
import com.ck.wi.model.dto.JobDto;
import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Job;
import com.ck.wi.service.IChecklist;
import com.ck.wi.service.IJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "https://oleo-soft.com", methods = {RequestMethod.GET, RequestMethod.POST})
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com"
})
@RestController
@RequestMapping("/api/v1")
public class ChecklistController {

    @Autowired
    private IChecklist checklistService;

    @Autowired
    private IJob jobService;

    @PutMapping("checklist")
    public ChecklistDto update(@RequestBody ChecklistDto checklistDto){
        Checklist checklistUpdate = checklistService.processAndSaveChecklist(checklistDto);

        return ChecklistDto.builder()
                .checklistsId(checklistUpdate.getChecklistsId())
                .employeesId(checklistUpdate.getEmployee().getEmployeesId())
                .jobsId(checklistUpdate.getJob().getJobsId())
                .equipmentsId(checklistUpdate.getEquipment().getEquipmentsId())
                .type(checklistUpdate.getType())
                .date(checklistUpdate.getDate())
                .odometer(checklistUpdate.getOdometer())
                .oil(checklistUpdate.getOil())
                .hydraulic(checklistUpdate.getHydraulic())
                .filter(checklistUpdate.getFilter())
                .radiator(checklistUpdate.getRadiator())
                .track(checklistUpdate.getTrack())
                .attachment(checklistUpdate.getAttachment())
                .leaking(checklistUpdate.getLeaking())
                .diesel(checklistUpdate.getDiesel())
                .clean(checklistUpdate.getClean())
                .comment(checklistUpdate.getComment())
                .build();
    }

    @DeleteMapping("checklist/{id}")
    public void delete(@PathVariable Integer id){
        Checklist checklist = checklistService.findById(id);
        checklistService.delete(checklist);
    }

    @GetMapping("checklist")
    public List<ChecklistDto> showAll() {
        List<Checklist> checklists = checklistService.findAll();

        return checklists.stream()
                .map( checklist ->
                        ChecklistDto.builder()
                                .checklistsId(checklist.getChecklistsId())
                                .employeesId(checklist.getEmployee().getEmployeesId())
                                .jobsId(checklist.getJob().getJobsId())
                                .equipmentsId(checklist.getEquipment().getEquipmentsId())
                                .type(checklist.getType())
                                .date(checklist.getDate())
                                .odometer(checklist.getOdometer())
                                .oil(checklist.getOil())
                                .hydraulic(checklist.getHydraulic())
                                .filter(checklist.getFilter())
                                .radiator(checklist.getRadiator())
                                .track(checklist.getTrack())
                                .attachment(checklist.getAttachment())
                                .leaking(checklist.getLeaking())
                                .diesel(checklist.getDiesel())
                                .clean(checklist.getClean())
                                .comment(checklist.getComment())
                                .build())
                .collect(Collectors.toList());
    }

//    @GetMapping("checklist/job/{jobNumber}")
//    public List<ChecklistDto> getChecklistsByJobNumber(@PathVariable String jobNumber) {
//        Job job = jobService.findByNumber(jobNumber);
//        Checklist checklist = checklistService.findByJob(job);
//
//        return ChecklistDto.builder()
//                .checklistsId(checklist.getChecklistsId())
//                .employeesId(checklist.getEmployee().getEmployeesId())
//                .jobsId(checklist.getJob().getJobsId())
//                .equipmentsId(checklist.getEquipment().getEquipmentsId())
//                .type(checklist.getType())
//                .date(checklist.getDate())
//                .odometer(checklist.getOdometer())
//                .oil(checklist.getOil())
//                .hydraulic(checklist.getHydraulic())
//                .filter(checklist.getFilter())
//                .radiator(checklist.getRadiator())
//                .track(checklist.getTrack())
//                .attachment(checklist.getAttachment())
//                .leaking(checklist.getLeaking())
//                .diesel(checklist.getDiesel())
//                .clean(checklist.getClean())
//                .comment(checklist.getComment())
//                .build();
//    }

    @GetMapping("checklist/{id}")
    public ChecklistDto getChecklistById(@PathVariable Integer id) {
        Checklist checklist = checklistService.findById(id);

        return ChecklistDto.builder()
                .checklistsId(checklist.getChecklistsId())
                .employeesId(checklist.getEmployee().getEmployeesId())
                .jobsId(checklist.getJob().getJobsId())
                .equipmentsId(checklist.getEquipment().getEquipmentsId())
                .type(checklist.getType())
                .date(checklist.getDate())
                .odometer(checklist.getOdometer())
                .oil(checklist.getOil())
                .hydraulic(checklist.getHydraulic())
                .filter(checklist.getFilter())
                .radiator(checklist.getRadiator())
                .track(checklist.getTrack())
                .attachment(checklist.getAttachment())
                .leaking(checklist.getLeaking())
                .diesel(checklist.getDiesel())
                .clean(checklist.getClean())
                .comment(checklist.getComment())
                .build();
    }

    @PostMapping("/checklist")
    public ChecklistDto submitChecklist(@RequestBody ChecklistDto checklistDto) {
        Checklist checklistSave = checklistService.processAndSaveChecklist(checklistDto);

        return ChecklistDto.builder()
                .checklistsId(checklistSave.getChecklistsId())
                .employeesId(checklistSave.getEmployee().getEmployeesId())
                .jobsId(checklistSave.getJob().getJobsId())
                .equipmentsId(checklistSave.getEquipment().getEquipmentsId())
                .type(checklistSave.getType())
                .date(checklistSave.getDate())
                .odometer(checklistSave.getOdometer())
                .oil(checklistSave.getOil())
                .hydraulic(checklistSave.getHydraulic())
                .filter(checklistSave.getFilter())
                .radiator(checklistSave.getRadiator())
                .track(checklistSave.getTrack())
                .attachment(checklistSave.getAttachment())
                .leaking(checklistSave.getLeaking())
                .diesel(checklistSave.getDiesel())
                .clean(checklistSave.getClean())
                .comment(checklistSave.getComment())
                .build();
    }
}
