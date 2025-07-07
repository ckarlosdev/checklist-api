package com.ck.wi.controller.silica;

import com.ck.wi.model.dto.silica.SilicaControlCreateDto;
import com.ck.wi.model.dto.silica.SilicaControlDto;
import com.ck.wi.model.dto.silica.SilicaCreateDto;
import com.ck.wi.model.dto.silica.SilicaDto;
import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.silica.Silica;
import com.ck.wi.model.entity.silica.SilicaControl;
import com.ck.wi.service.IJob;
import com.ck.wi.service.silica.IControlsDescription;
import com.ck.wi.service.silica.ISilica;
import com.ck.wi.service.silica.ISilicaControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class SilicaController {

    @Autowired
    private ISilica silicaService;

    @Autowired
    private IJob jobService;

    @Autowired
    private ISilicaControl silicaControlService;

    @PostMapping("silica")
    public SilicaCreateDto submitSilica(@RequestBody SilicaCreateDto silicaCreateDto){
        Silica silica = silicaService.processAndSaveSilica(silicaCreateDto);

        List<SilicaControlCreateDto> silicaControlDtos =
                Optional.ofNullable(silica.getSilicaControls())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map( silicaControl ->
                                SilicaControlCreateDto.builder()
                                        .silicaControlId(silicaControl.getSilicaControlsId())
                                        .controlDescriptionId(silicaControl.getControlsDescription().getControlsDescriptionsId())
                                        .controlAnswer(silicaControl.getControlAnswer())
                                        .build())
                        .collect(Collectors.toList());

        return SilicaCreateDto.builder()
                .silicaId(silica.getSilicaId())
                .jobsId(silica.getJob().getJobsId())
                .employeesId(silica.getEmployee().getEmployeesId())
                .eventDate(silica.getEventDate())
                .workDescription(silica.getWorkDescription())
                .diagramId(silica.getDiagramId())
                .diagramFolder(silica.getDiagramFolder())
                .ventilationArea(silica.getVentilationArea())
                .datePlan(silica.getDatePlan())
                .equipmentDescription(silica.getEquipmentDescription())
                .signatureId(silica.getSignatureId())
                .signatureFolder(silica.getSignatureFolder())
                .silicaControls(silicaControlDtos)
                .build();
    }

    @PutMapping("silica")
    public SilicaCreateDto updateSilica(@RequestBody SilicaCreateDto silicaCreateDto){
        Silica silica = silicaService.processAndSaveSilica(silicaCreateDto);

        List<SilicaControlCreateDto> silicaControlDtos =
                Optional.ofNullable(silica.getSilicaControls())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map( silicaControl ->
                                SilicaControlCreateDto.builder()
                                        .silicaControlId(silicaControl.getSilicaControlsId())
                                        .controlDescriptionId(silicaControl.getControlsDescription().getControlsDescriptionsId())
                                        .controlAnswer(silicaControl.getControlAnswer())
                                        .build())
                        .collect(Collectors.toList());

        return SilicaCreateDto.builder()
                .silicaId(silica.getSilicaId())
                .jobsId(silica.getJob().getJobsId())
                .employeesId(silica.getEmployee().getEmployeesId())
                .eventDate(silica.getEventDate())
                .workDescription(silica.getWorkDescription())
                .diagramId(silica.getDiagramId())
                .diagramFolder(silica.getDiagramFolder())
                .ventilationArea(silica.getVentilationArea())
                .datePlan(silica.getDatePlan())
                .equipmentDescription(silica.getEquipmentDescription())
                .signatureId(silica.getSignatureId())
                .signatureFolder(silica.getSignatureFolder())
                .silicaControls(silicaControlDtos)
                .build();
    }

    @GetMapping("silica/{number}/by-date")
    public List<SilicaDto> getSilicaByNumberAndDate(
            @PathVariable String number,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ){
        Job job = jobService.findByNumber(number);
        List<Silica> silicas = silicaService.findByJobAndEventDate(job, date);
        List<SilicaDto> silicaDtos = new ArrayList<>();

        silicas.forEach( silica -> {
            List<SilicaControlDto> silicaControlDtos =
                    silica.getSilicaControls().stream()
                            .map( silicaControl ->
                                    SilicaControlDto.builder()
                                            .silicaControlId(silicaControl.getSilicaControlsId())
                                            .controlDescription(silicaControl.getControlsDescription())
                                            .controlAnswer(silicaControl.getControlAnswer())
                                            .build())
                            .collect(Collectors.toList());


            SilicaDto silicaDto = SilicaDto.builder()
                    .silicaId(silica.getSilicaId())
                    .job(silica.getJob())
                    .employee(silica.getEmployee())
                    .eventDate(silica.getEventDate())
                    .workDescription(silica.getWorkDescription())
                    .diagramId(silica.getDiagramId())
                    .diagramFolder(silica.getDiagramFolder())
                    .ventilationArea(silica.getVentilationArea())
                    .datePlan(silica.getDatePlan())
                    .equipmentDescription(silica.getEquipmentDescription())
                    .signatureId(silica.getSignatureId())
                    .signatureFolder(silica.getSignatureFolder())
                    .silicaControls(silicaControlDtos)
                    .build();

            silicaDtos.add(silicaDto);
        });

        return silicaDtos;
    }
}
