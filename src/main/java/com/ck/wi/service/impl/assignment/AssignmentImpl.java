package com.ck.wi.service.impl.assignment;

import com.ck.wi.mapper.AssignmentMapper;
import com.ck.wi.model.dao.JobDao;
import com.ck.wi.model.dao.assignment.AssignmentDao;
import com.ck.wi.model.dao.assignment.AssignmentEmployeeDao;
import com.ck.wi.model.dao.assignment.AssignmentJobDao;
import com.ck.wi.model.dto.assignment.*;
import com.ck.wi.model.entity.assignment.Assignment;
import com.ck.wi.model.entity.assignment.AssignmentEmployee;
import com.ck.wi.model.entity.assignment.AssignmentJob;
import com.ck.wi.service.assigment.IAssignment;
import com.ck.wi.service.assigment.IAssignmentJob;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AssignmentImpl implements IAssignment {

    @Autowired
    private AssignmentDao assignmentDao;

    @Autowired
    private AssignmentJobDao assignmentJobDao;

    @Autowired
    private AssignmentEmployeeDao assignmentEmployeeDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private AssignmentMapper mapper;

    @Transactional
    @Override
    public AssignmentDto save(AssignmentCreateDto assignmentCreateDto){

        if(assignmentCreateDto == null){
            throw new IllegalArgumentException("data must not be null.");
        }

        Assignment assignmentSaved = saveOrUpdateAssignment(assignmentCreateDto);
        Integer newAssignmentId = assignmentSaved.getAssignmentsId();

        List<AssignmentJob> existingJobs = assignmentJobDao.findByAssignmentId(newAssignmentId);

        Map<Integer, AssignmentJob> existingJobsMap = existingJobs.stream()
                .collect(Collectors.toMap(AssignmentJob::getJobsId, Function.identity()));

        Set<Integer> jobsInDto = new HashSet<>();

        if (assignmentCreateDto.getAssignmentJobCreateDtoList() != null) {
            for (AssignmentJobCreateDto jobDto : assignmentCreateDto.getAssignmentJobCreateDtoList()) {

                jobsInDto.add(jobDto.getId());

                AssignmentJob currentJobEntity = saveOrUpdateAssignmentJob(
                        assignmentSaved, jobDto, existingJobsMap
                );

                // El Job se guarda aquí para asegurar que tenemos un 'assignmentJobId' si es nuevo.
                AssignmentJob assignmentJobSaved = assignmentJobDao.save(currentJobEntity);

                // Procesar la Entidad AssignmentEmployee (Hijos)
                if (jobDto.getAssignedEmployeeIds() != null) {
                    processAssignmentEmployees(
//                            currentJobEntity,
                            assignmentJobSaved,
                            jobDto.getAssignedEmployeeIds()
                    );
                }
            }
        }

        applySoftDeleteAssignmentJob(existingJobs, jobsInDto);

        Assignment assignmentFinal = assignmentDao.findFullAssignmentById(newAssignmentId)
                .orElseThrow(() -> new RuntimeException("No se pudo recargar la asignación después de guardar."));
        return mapper.toDto(assignmentFinal);
    }

    private Assignment saveOrUpdateAssignment(AssignmentCreateDto dto) {
        Assignment assignment;

        if (dto.getAssignmentId() != null && dto.getAssignmentId() != 0) {
            assignment = assignmentDao.findById(dto.getAssignmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Assignment con ID " + dto.getAssignmentId() + " no encontrado."));

            assignment.setStartDate(dto.getStartDate());
            assignment.setEndDate(dto.getEndDate());
//            assignment.setUpdatedBy(dto.getCreatedBy()); // Usar updatedBy en un update
//            assignment.setUpdatedDate(LocalDateTime.now());
            assignment.setAssignmentStatus("1");
        } else {
            assignment = Assignment.builder()
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .createdBy(dto.getCreatedBy())
                    .createdDate(LocalDateTime.now())
                    .assignmentStatus("1")
                    .build();
        }
        return assignmentDao.save(assignment);
    }

    private AssignmentJob saveOrUpdateAssignmentJob(Assignment assignment, AssignmentJobCreateDto jobDto, Map<Integer, AssignmentJob> existingJobsMap) {
        AssignmentJob currentJobEntity;

        if (existingJobsMap.containsKey(jobDto.getId())) {
            // ACT (Actualizar/Reactivar)
            currentJobEntity = existingJobsMap.get(jobDto.getId());
            currentJobEntity.setAjStatus("1"); // Asegurar que está activo
        } else {
            // CREAR
            currentJobEntity = AssignmentJob.builder()
                    .assignment(assignment)
                    .jobsId(jobDto.getId())
                    .ajStatus("1")
                    .startTime(jobDto.getStartTime())
                    .assignmenComment(jobDto.getAssignmentComment())
                    .build();
        }
        return currentJobEntity;
    }

    private void applySoftDeleteAssignmentJob(List<AssignmentJob> existingJobs, Set<Integer> jobsInDto) {
        List<AssignmentJob> jobsToDelete = existingJobs.stream()
                .filter(job -> !jobsInDto.contains(job.getJobsId()) && "1".equals(job.getAjStatus()))
                .peek(job -> {
                    job.setAjStatus("0");

                    assignmentEmployeeDao.updateStatusByAssignmentJobId(job.getAssignmentJobId(), "0");
                })
                .collect(Collectors.toList());

        if (!jobsToDelete.isEmpty()) {
            assignmentJobDao.saveAll(jobsToDelete);
        }
    }

    private void processAssignmentEmployees(AssignmentJob assignmentJob, Integer[] assignedEmployeeIds) {

        // 1. Cargar existentes y preparar colecciones
        List<AssignmentEmployee> existingEmployees = assignmentEmployeeDao.findByAssignmentJobId(assignmentJob.getAssignmentJobId());
        Map<Integer, AssignmentEmployee> existingEmployeeMap = existingEmployees.stream()
                .collect(Collectors.toMap(AssignmentEmployee::getEmployeesId, Function.identity()));

        List<AssignmentEmployee> employeesToPersist = new ArrayList<>();
        Set<Integer> employeesInDto = assignedEmployeeIds != null ? Set.of(assignedEmployeeIds) : Collections.emptySet();

        // 2. Creación y Reactivación
        for (Integer employeeId : employeesInDto) {
            if (existingEmployeeMap.containsKey(employeeId)) {
                // ACT/REACTIVAR
                AssignmentEmployee ae = existingEmployeeMap.get(employeeId);
                if ("0".equals(ae.getAeStatus())) {
                    ae.setAeStatus("1");
                    employeesToPersist.add(ae); // Agregar para guardar
                }
            } else {
                // CREAR
                AssignmentEmployee newEmployee = AssignmentEmployee.builder()
                        .assignmentJob(assignmentJob)
                        .employeesId(employeeId)
                        .aeStatus("1")
                        .build();
                employeesToPersist.add(newEmployee);
            }
        }

        // 3. Eliminación Lógica
        existingEmployees.stream()
                .filter(ae -> !employeesInDto.contains(ae.getEmployeesId()) && "1".equals(ae.getAeStatus()))
                .forEach(ae -> {
                    ae.setAeStatus("0");
                    employeesToPersist.add(ae); // Agregar para guardar (cambio de estado)
                });

        // Guardar todos los cambios (INSERT/UPDATE del estado) en un solo lote
        if (!employeesToPersist.isEmpty()) {
            assignmentEmployeeDao.saveAll(employeesToPersist);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public AssignmentDto getById(Integer assignmentsId){

        Assignment assignmentFinal = assignmentDao.findFullAssignmentById(assignmentsId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment with ID " + assignmentsId + " not found."));

//        System.out.println("Jobs: " + assignmentFinal.getAssignmentJobs().size());
//        assignmentFinal.getAssignmentJobs().forEach(job ->
//                System.out.println("  Employees: " + job.getAssignmentEmployees().size())
//        );

        return mapper.toDto(assignmentFinal);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Assignment> getAssignments(){
        return (List<Assignment>) assignmentDao.findByAssignmentStatus("1");
    }
}
