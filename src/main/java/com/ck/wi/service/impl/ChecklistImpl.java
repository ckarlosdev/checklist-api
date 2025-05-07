package com.ck.wi.service.impl;

import com.ck.wi.model.dao.ChecklistDao;
import com.ck.wi.model.dao.EmployeeDao;
import com.ck.wi.model.dao.EquipmentDao;
import com.ck.wi.model.dao.JobDao;
import com.ck.wi.model.dto.ChecklistDto;
import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Employee;
import com.ck.wi.model.entity.Equipment;
import com.ck.wi.model.entity.Job;
import com.ck.wi.service.IChecklist;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChecklistImpl implements IChecklist {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private ChecklistDao checklistDao;

    @Transactional
    @Override
    public Checklist processAndSaveChecklist(ChecklistDto checklistDto) {
        Optional<Employee> employee = employeeDao.findById(checklistDto.getEmployeesId());
        Optional<Job> job = jobDao.findById(checklistDto.getJobsId());
        Optional<Equipment> equipment = equipmentDao.findById(checklistDto.getEquipmentsId());

        if (employee.isPresent() && job.isPresent() && equipment.isPresent()) {

            Checklist checklist = Checklist.builder()
                    .checklistsId(checklistDto.getChecklistsId())
                    .employee(employee.get())
                    .job(job.get())
                    .equipment(equipment.get())
                    .type(checklistDto.getType())
                    .date(checklistDto.getDate())
                    .odometer(checklistDto.getOdometer())
                    .oil(checklistDto.getOil())
                    .hydraulic(checklistDto.getHydraulic())
                    .filter(checklistDto.getFilter())
                    .radiator(checklistDto.getRadiator())
                    .track(checklistDto.getTrack())
                    .attachment(checklistDto.getAttachment())
                    .leaking(checklistDto.getLeaking())
                    .diesel(checklistDto.getDiesel())
                    .clean(checklistDto.getClean())
                    .comment(checklistDto.getComment())
                    .createdDate(checklistDto.getCreatedDate())
                    .createdBy(checklistDto.getCreatedBy())
                    .updatedBy(checklistDto.getUpdatedBy())
                    .updatedDate(checklistDto.getUpdatedDate())
                    .status(checklistDto.getStatus())
                    .build();

            return checklistDao.save(checklist);
        } else {
            throw new IllegalArgumentException("Empleado, trabajo o equipo no encontrado.");
        }
    }

    @Override
    public Checklist findById(Integer id) {
        return checklistDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Checklist checklist) {
        checklistDao.delete(checklist);
    }
}
