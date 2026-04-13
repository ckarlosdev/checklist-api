package com.ck.wi.mapper;
import com.ck.wi.model.dao.assignment.AssignmentAbsenceDao;
import com.ck.wi.model.dto.assignment.AbsenceDto;
import com.ck.wi.model.dto.assignment.AssignmentEmployeeDto;
import com.ck.wi.model.dto.assignment.AssignmentJobDto;
import com.ck.wi.model.entity.assignment.AssignmentAbsence;
import com.ck.wi.model.entity.assignment.AssignmentEmployee;
import com.ck.wi.model.entity.assignment.AssignmentJob;
import org.hibernate.Hibernate;
import org.mapstruct.Mapper;

import com.ck.wi.model.dto.assignment.AssignmentDto;
import com.ck.wi.model.entity.assignment.Assignment;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    default AssignmentDto toDto(Assignment assignment) {
        if (assignment == null) return null;

        AssignmentDto dto = new AssignmentDto();
        dto.setAssignmentsId(assignment.getAssignmentsId());
        dto.setStartDate(assignment.getStartDate());
        dto.setEndDate(assignment.getEndDate());
        dto.setCreatedBy(assignment.getCreatedBy());

        dto.setAssignmentJobDtos(toAssignmentJobDtos(assignment.getAssignmentJobs()));
        dto.setAbsences(toAbsenceDtos(assignment.getAssignmentAbsences()));

        return dto;
    }

    default List<AssignmentJobDto> toAssignmentJobDtos(Set<AssignmentJob> assignmentJobs) {
        if (assignmentJobs == null) {
            return Collections.emptyList();
        }

        return assignmentJobs.stream()
                .map(this::toAssignmentJobDto) // llama al método implementado abajo
                .collect(Collectors.toList());
    }

    default List<AbsenceDto> toAbsenceDtos(Set<AssignmentAbsence> assignmentAbsences){
        if (assignmentAbsences == null || !Hibernate.isInitialized(assignmentAbsences)) {
            return Collections.emptyList();
        }

        return assignmentAbsences.stream()
                .filter(a -> "1".equals(a.getAbsenceStatus())) // Filtra aquí los activos!
                .map(this::toAssignmentAbsenceDto)
                .collect(Collectors.toList());
    }

    default AbsenceDto toAssignmentAbsenceDto(AssignmentAbsence absence){
        if( absence == null ) return null;

        AbsenceDto dto = new AbsenceDto();
        dto.setAssignmentsAbsencesId(absence.getAssignmentsAbsencesId());
        dto.setEmployeesId(absence.getEmployeesId());
        dto.setAbsenceType(absence.getAbsenceType());
        dto.setComments(absence.getComments());

        return dto;
    }

    // Implementación real del mapeo de un Job
    default AssignmentJobDto toAssignmentJobDto(AssignmentJob job) {
        if (job == null) return null;

        AssignmentJobDto dto = new AssignmentJobDto();
        dto.setJobsId(job.getJobsId());
        dto.setStartTime(job.getStartTime());
        dto.setAssignmentComment(job.getAssignmenComment());

        // Mapear empleados del job
        dto.setAssignmentEmployeeDtos(
                job.getAssignmentEmployees().stream()
                        .map(this::toAssignmentEmployeeDto)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    // Implementación real del mapeo de un Employee
    default AssignmentEmployeeDto toAssignmentEmployeeDto(AssignmentEmployee emp) {
        if (emp == null) return null;

        AssignmentEmployeeDto dto = new AssignmentEmployeeDto();
        dto.setAssignmentsEmployeesId(emp.getAssignmentsEmployeesId());
        dto.setEmployeesId(emp.getEmployeesId());

        return dto;
    }
}

