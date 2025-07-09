package com.ck.wi.service.impl.silica;

import com.ck.wi.model.dao.EmployeeDao;
import com.ck.wi.model.dao.JobDao;
import com.ck.wi.model.dao.silica.ControlDescriptionDao;
import com.ck.wi.model.dao.silica.SilicaControlDao;
import com.ck.wi.model.dao.silica.SilicaDao;
import com.ck.wi.model.dto.silica.*;
import com.ck.wi.model.entity.Employee;
import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.silica.ControlsDescription;
import com.ck.wi.model.entity.silica.Silica;
import com.ck.wi.model.entity.silica.SilicaControl;
import com.ck.wi.service.IEmployee;
import com.ck.wi.service.silica.ISilica;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SilicaImpl implements ISilica {

    @Autowired
    private SilicaDao silicaDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private SilicaControlDao silicaControlDao;

    @Autowired
    private ControlDescriptionDao controlDescriptionDao;

    @Override
    public List<Silica> findByJobAndEventDate(Job job, LocalDate eventDate){
        return (List<Silica>) silicaDao.findByJobAndEventDate(job, eventDate);
    }

    @Override
    public List<Silica> findByJob(Job job){
        return (List<Silica>) silicaDao.findByJobOrderByEventDateDesc(job);
    }


    @Transactional
    @Override
    public Silica processAndSaveSilica(SilicaCreateDto silicaCreateDto){
        if (silicaCreateDto.getJobsId() == null || silicaCreateDto.getEmployeesId() == null) {
            throw new IllegalArgumentException("Job ID and Employee ID must not be null");
        }

        Optional<Employee> employeeOpt = employeeDao.findById(silicaCreateDto.getEmployeesId());
        Optional<Job> jobOpt = jobDao.findById(silicaCreateDto.getJobsId());

        LocalDateTime today = LocalDateTime.now();

        if(employeeOpt.isPresent() && jobOpt.isPresent()){
            Silica silica;

            // --- Determine if it's a CREATE or UPDATE ---
            if (silicaCreateDto.getSilicaId() != null && silicaCreateDto.getSilicaId() != 0) {
                // UPDATE: Fetch existing Silica entity
                silica = silicaDao.findById(silicaCreateDto.getSilicaId())
                        .orElseThrow(() -> new IllegalArgumentException("Silica not found for ID: " + silicaCreateDto.getSilicaId()));

                // Update fields of the existing entity
                silica.setJob(jobOpt.get());
                silica.setEmployee(employeeOpt.get());
                silica.setEventDate(silicaCreateDto.getEventDate());
                silica.setWorkDescription(silicaCreateDto.getWorkDescription());
                silica.setVentilationArea(silicaCreateDto.getVentilationArea());
                silica.setDatePlan(silicaCreateDto.getDatePlan());
                silica.setEquipmentDescription(silicaCreateDto.getEquipmentDescription());
                silica.setSignatureId(silicaCreateDto.getSignatureId());
                silica.setSignatureFolder(silicaCreateDto.getSignatureFolder());
                silica.setDiagramData(silicaCreateDto.getDiagramData());
                silica.setUpdatedBy(silicaCreateDto.getUpdatedBy()); // Use a distinct 'updatedBy'
                silica.setUpdatedDate(today);
                // Status should probably not be hardcoded to "1" on update unless it's always active
                // silica.setStatus("1");
            } else {
                // CREATE: Build a new Silica entity (ID will be auto-generated)
                silica = Silica.builder()
                        .job(jobOpt.get())
                        .employee(employeeOpt.get())
                        .eventDate(silicaCreateDto.getEventDate())
                        .workDescription(silicaCreateDto.getWorkDescription())
//                        .diagramId(silicaCreateDto.getDiagramId())
//                        .diagramFolder(silicaCreateDto.getDiagramFolder())
                        .ventilationArea(silicaCreateDto.getVentilationArea())
                        .datePlan(silicaCreateDto.getDatePlan())
                        .equipmentDescription(silicaCreateDto.getEquipmentDescription())
                        .signatureId(silicaCreateDto.getSignatureId())
                        .signatureFolder(silicaCreateDto.getSignatureFolder())
                        .diagramData(silicaCreateDto.getDiagramData())
                        .createdBy(silicaCreateDto.getCreatedBy()) // Use a distinct 'createdBy'
                        .createdDate(today)
                        .updatedBy(silicaCreateDto.getCreatedBy())
                        .updatedDate(today)
                        .status("1")
                        .build();
            }

            Silica silicaSaved = silicaDao.save(silica); // Save/Update parent


            if (silicaSaved != null) {

                if (silicaCreateDto.getSilicaControls() == null || silicaCreateDto.getSilicaControls().isEmpty()) {
                    if (silicaSaved.getSilicaControls() != null) {
                        silicaSaved.getSilicaControls().clear(); // This triggers orphanRemoval
                    }
                } else {
                    // Map existing controls by ID for efficient lookup
                    Map<Integer, SilicaControl> existingControlsMap = silicaSaved.getSilicaControls() != null
                            ? silicaSaved.getSilicaControls().stream()
                            .filter(c -> c.getSilicaControlsId() != null) // Only map controls that exist in DB
                            .collect(Collectors.toMap(SilicaControl::getSilicaControlsId, c -> c))
                            : new java.util.HashMap<>();

                    List<SilicaControl> updatedControlsList = new ArrayList<>();

                    for (SilicaControlCreateDto silicaControlDto : silicaCreateDto.getSilicaControls()) {
                        ControlsDescription controlsDescription = controlDescriptionDao.findById(silicaControlDto.getControlDescriptionId())
                                .orElseThrow(() -> new IllegalArgumentException("ControlsDescription not found for ID: " + silicaControlDto.getControlDescriptionId()));

                        SilicaControl silicaControl;
                        if (silicaControlDto.getSilicaControlId() != null && silicaControlDto.getSilicaControlId() != 0 && existingControlsMap.containsKey(silicaControlDto.getSilicaControlId())) {

                            silicaControl = existingControlsMap.get(silicaControlDto.getSilicaControlId());
                            silicaControl.setControlsDescription(controlsDescription);
                            silicaControl.setControlAnswer(silicaControlDto.getControlAnswer());
                            silicaControl.setUpdatedBy(silicaCreateDto.getUpdatedBy());
                            silicaControl.setUpdatedDate(today);
                            silicaControl.setStatus("1"); // Again, consider if status should be hardcoded
                            existingControlsMap.remove(silicaControl.getSilicaControlsId()); // Remove from map, so remaining are orphans
                        } else {

                            silicaControl = SilicaControl.builder()
                                    .controlsDescription(controlsDescription)
                                    .controlAnswer(silicaControlDto.getControlAnswer())
                                    .createdBy(silicaCreateDto.getCreatedBy())
                                    .createdDate(today)
                                    .updatedBy(silicaCreateDto.getCreatedBy())
                                    .updatedDate(today)
                                    .status("1")
                                    .build();
                        }

                        silicaControl.setSilica(silicaSaved);
                        updatedControlsList.add(silicaControl);
                    }

                    if (silicaSaved.getSilicaControls() != null) {
                        silicaSaved.getSilicaControls().clear(); // Clear existing controls
                        silicaSaved.getSilicaControls().addAll(updatedControlsList); // Add the updated/new list
                    } else {
                        silicaSaved.setSilicaControls(updatedControlsList); // If collection was null, set it
                    }

                }
            }

            return silicaDao.save(silicaSaved);

        } else {
            throw new IllegalArgumentException("Employee or job not found.");
        }
    }

    @Transactional
    @Override
    public Optional<SilicaDto> getSilicaWithControls(Integer silicaId) {

        Optional<Silica> silicaOptional = silicaDao.findById(silicaId);

        return silicaOptional.map(silica -> {
//            List<SilicaControlDto> controls = silica.getSilicaControls();

            List<SilicaControlDto> silicaControlDtos =
                    silica.getSilicaControls().stream()
                            .map( silicaControl ->
                                    SilicaControlDto.builder()
                                            .silicaControlId(silicaControl.getSilicaControlsId())
                                            .controlDescription(silicaControl.getControlsDescription())
                                            .controlAnswer(silicaControl.getControlAnswer())
                                            .build())
                            .collect(Collectors.toList());

            return new SilicaDto(
                    silica.getSilicaId(),
                    silica.getJob(),
                    silica.getEmployee(),
                    silica.getEventDate(),
                    silica.getWorkDescription(),
//                    silica.getDiagramId(),
//                    silica.getDiagramFolder(),
                    silica.getVentilationArea(),
                    silica.getDatePlan(),
                    silica.getEquipmentDescription(),
                    silica.getSignatureId(),
                    silica.getSignatureFolder(),
                    silicaControlDtos,
                    silica.getDiagramData(),
                    silica.getCreatedBy(),
                    silica.getUpdatedBy());
        });
    }
}
