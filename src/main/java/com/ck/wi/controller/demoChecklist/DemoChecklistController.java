package com.ck.wi.controller.demoChecklist;

import com.ck.wi.model.dto.demoChecklist.DemoChecklistCreateDto;
import com.ck.wi.model.dto.demoChecklist.DemoChecklistsItemDto;
import com.ck.wi.model.entity.demoChecklist.DemoChecklist;
import com.ck.wi.service.IJob;
import com.ck.wi.service.demoChecklist.IDemoChecklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class DemoChecklistController {

    @Autowired
    private IDemoChecklist demoChecklistService;

    @Autowired
    private IJob jobService;

    @PostMapping("demoChecklist")
    public DemoChecklistCreateDto submitDemoChecklist(@RequestBody DemoChecklistCreateDto demoChecklistCreateDto){
        DemoChecklist demoChecklist = demoChecklistService.processAndSaveDemoChecklist(demoChecklistCreateDto);

        List<DemoChecklistsItemDto> demoChecklistsItemDtos =
                Optional.ofNullable(demoChecklist.getItems())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(demoChecklistsItem ->
                                DemoChecklistsItemDto.builder()
                                        .demoChecklistsItemsId(demoChecklistsItem.getDemoChecklistsItemsId())
                                        .demoChecklistsId(demoChecklistsItem.getDemoChecklist().getDemoChecklistsId())
                                        .demoItemsId(demoChecklistsItem.getDemoItem().getDemoItemsId())
                                        .response(demoChecklistsItem.getResponse())
                                        .dciStatus(demoChecklistsItem.getDciStatus())
                                        .build())
                        .collect(Collectors.toList());

        return DemoChecklistCreateDto.builder()
                .demoChecklistsId(demoChecklist.getDemoChecklistsId())
                .jobsId(demoChecklist.getJob().getJobsId())
                .checklistDate(demoChecklist.getChecklistDate())
                .buildingType(demoChecklist.getBuildingType())
                .foreman(demoChecklist.getForeman())
                .notes(demoChecklist.getNotes())
                .signature(demoChecklist.getSignature())
                .permits(demoChecklist.getPermits())
                .items(demoChecklistsItemDtos)
                .createdBy(demoChecklist.getCreatedBy())
                .updatedBy(demoChecklist.getUpdatedBy())
                .build();

    }

    @PutMapping("demoChecklist")
    public DemoChecklistCreateDto updateDemoChecklist(@RequestBody DemoChecklistCreateDto demoChecklistCreateDto){
        DemoChecklist demoChecklist = demoChecklistService.processAndSaveDemoChecklist(demoChecklistCreateDto);

        List<DemoChecklistsItemDto> demoChecklistsItemDtos =
                Optional.ofNullable(demoChecklist.getItems())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(demoChecklistsItem ->
                                DemoChecklistsItemDto.builder()
                                        .demoChecklistsItemsId(demoChecklistsItem.getDemoChecklistsItemsId())
                                        .demoChecklistsId(demoChecklistsItem.getDemoChecklist().getDemoChecklistsId())
                                        .demoItemsId(demoChecklistsItem.getDemoItem().getDemoItemsId())
                                        .response(demoChecklistsItem.getResponse())
                                        .dciStatus(demoChecklistsItem.getDciStatus())
                                        .build())
                        .collect(Collectors.toList());

        return DemoChecklistCreateDto.builder()
                .demoChecklistsId(demoChecklist.getDemoChecklistsId())
                .jobsId(demoChecklist.getJob().getJobsId())
                .checklistDate(demoChecklist.getChecklistDate())
                .buildingType(demoChecklist.getBuildingType())
                .foreman(demoChecklist.getForeman())
                .notes(demoChecklist.getNotes())
                .signature(demoChecklist.getSignature())
                .permits(demoChecklist.getPermits())
                .items(demoChecklistsItemDtos)
                .createdBy(demoChecklist.getCreatedBy())
                .updatedBy(demoChecklist.getUpdatedBy())
                .build();

    }

    @GetMapping("demoChecklist/{demoChecklistId}")
    public Optional<DemoChecklistCreateDto>  getDemoChecklist(@PathVariable Integer demoChecklistId){

        return demoChecklistService.getDemoChecklistByID(demoChecklistId);

    }
}
