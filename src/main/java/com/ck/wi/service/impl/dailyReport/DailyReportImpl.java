package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.AttachmentDao;
import com.ck.wi.model.dao.EmployeeDao;
import com.ck.wi.model.dao.EquipmentDao;
import com.ck.wi.model.dao.JobDao;
import com.ck.wi.model.dao.dailyReport.*;
import com.ck.wi.model.dto.dailyReport.DailyReportDto;
import com.ck.wi.model.dto.dailyReport.DailyReportGralDto;
import com.ck.wi.model.dto.dailyReport.DailyReportSummaryDto;
import com.ck.wi.model.dto.dailyReport.creation.*;
import com.ck.wi.model.entity.Attachment;
import com.ck.wi.model.entity.Employee;
import com.ck.wi.model.entity.Equipment;
import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.dailyReport.*;
import com.ck.wi.service.dailyReport.IDailyReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DailyReportImpl implements IDailyReport {

    @Autowired
    private DailyReportDao dailyReportDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DrEmployeeDao drEmployeeDao;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private DrEquipmentDao drEquipmentDao;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private DrRentalDao drRentalDao;

    @Autowired
    private ToolDao toolDao;

    @Autowired
    private DrDumpsterDao drDumpsterDao;

    private static final String ACTIVE = "1";
    private static final String INACTIVE = "0";

    @Override
    public DailyReportCreateDto findByDailyReportID(Integer dailyReportId){
        DailyReport dailyReport = dailyReportDao.findById(dailyReportId)
                .orElseThrow(() -> new IllegalArgumentException("Daily Report not found"));

        DailyReportCreateDto response = toDto(dailyReport);
//        return response;
        return allDto(response, dailyReportId);
    }

    @Override
    public DailyReportCreateDto update(DailyReportCreateDto dailyReportCreateDto){
        DailyReport dailyReportToUpdate = dailyReportDao.findById(dailyReportCreateDto.getDailyReportId())
                .orElseThrow(() -> new IllegalArgumentException("Daily Report not found"));

        LocalDateTime now = LocalDateTime.now();

        dailyReportToUpdate.setDate(dailyReportCreateDto.getDate());
        dailyReportToUpdate.setForeman(dailyReportCreateDto.getForeman());
        dailyReportToUpdate.setDescription(dailyReportCreateDto.getDescription());
        dailyReportToUpdate.setManOther(dailyReportCreateDto.getManOther());
        dailyReportToUpdate.setEquipmentOther(dailyReportCreateDto.getEquipmentOther());
        dailyReportToUpdate.setIssues(dailyReportCreateDto.getIssues());
        dailyReportToUpdate.setUpdatedBy(dailyReportCreateDto.getUserName());
        dailyReportToUpdate.setUpdatedDate(now);

        DailyReport dailyReportUpdated = dailyReportDao.save(dailyReportToUpdate);
        udpateEmployees(dailyReportCreateDto.getEmployees(), dailyReportUpdated);
        updateEquipments(dailyReportCreateDto.getEquipments(), dailyReportUpdated);
        updateRentals(dailyReportCreateDto.getRentals(), dailyReportUpdated);
        updateTools(dailyReportCreateDto.getTools(), dailyReportUpdated);
        updateDumpsters(dailyReportCreateDto.getDumpsters(), dailyReportUpdated);

        DailyReportCreateDto response = toDto(dailyReportUpdated);
        return allDto(response, dailyReportCreateDto.getDailyReportId());
    }

    private DailyReportCreateDto allDto(DailyReportCreateDto response, Integer dailyReportId){
        List<DrEmployee> actualDrEmployees = drEmployeeDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);
        List<DrEmployeeCreateDto> employeesDto =
                actualDrEmployees.stream()
                        .map(this::toEmployeeDto)
                        .toList();
        List<DrEquipment> actualDrEquipments = drEquipmentDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);
        List<DrEquipmentCreateDto> equipmentsDto =
                actualDrEquipments.stream()
                        .map(this::toEquipmentDto)
                        .toList();
        List<DrRental> actualDrRentals = drRentalDao.findByDailyReportIdAndRentalsStatus(dailyReportId, ACTIVE);
        List<DrRentalCreateDto> rentalsDto =
                actualDrRentals.stream()
                        .map(this::toRentalDto)
                        .toList();
        List<Tool> actualDrTools = toolDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);
        List<DrToolCreateDto> toolsDto =
                actualDrTools.stream()
                        .map(this::toToolDto)
                        .toList();
        List<DrDumpster> actualDrDumpsters = drDumpsterDao.findByDailyReportIdAndDumpstersStatus(dailyReportId, ACTIVE);
        List<DrDumpsterCreateDto> dumpstersDto =
                actualDrDumpsters.stream()
                        .map(this::toDumpsterDto)
                        .toList();

        response.setEmployees(employeesDto);
        response.setEquipments(equipmentsDto);
        response.setRentals(rentalsDto);
        response.setTools(toolsDto);
        response.setDumpsters(dumpstersDto);

        return response;
    }

    private void updateDumpsters(List<DrDumpsterCreateDto> dummpstersDto, DailyReport dailyReport){
        Integer dailyReportId = dailyReport.getDailyReportId();
        LocalDateTime now = LocalDateTime.now();
        String updater = dailyReport.getUpdatedBy();

        List<DrDumpster> actualDrDumpsters = drDumpsterDao.findByDailyReportIdAndDumpstersStatus(dailyReportId, ACTIVE);
        Map<Integer, DrDumpster> actualDumpstersMap = actualDrDumpsters.stream()
                .collect(Collectors.toMap(DrDumpster::getDrDumpstersId, Function.identity()));
        List<DrDumpster> dumpstersTosave = new ArrayList<>();

        for(DrDumpsterCreateDto dto: dummpstersDto){
            Integer dtoId = dto.getDrDumpstersId();
            if(dtoId != null && actualDumpstersMap.containsKey(dtoId)){
                // update
                DrDumpster drDumpster = actualDumpstersMap.get(dtoId);
                drDumpster.setSourceDumpster(dto.getSourceDumpster());
                drDumpster.setSizeDumpster(dto.getSizeDumpster());
                drDumpster.setTypeDumpster(dto.getTypeDumpster());
                drDumpster.setQuantity(dto.getQuantity());
                drDumpster.setUpdatedBy(updater);
                drDumpster.setUpdatedDate(now);
                drDumpster.setDumpstersStatus(ACTIVE);
                dumpstersTosave.add(drDumpster);
                actualDumpstersMap.remove(dtoId);
            }else{
                // create
                DrDumpster newDumpster = DrDumpster.builder()
                        .dailyReportId(dailyReportId)
                        .sourceDumpster(dto.getSourceDumpster())
                        .sizeDumpster(dto.getSizeDumpster())
                        .typeDumpster(dto.getTypeDumpster())
                        .quantity(dto.getQuantity())
                        .createdBy(updater)
                        .createdDate(now)
                        .updatedBy(updater)
                        .updatedDate(now)
                        .dumpstersStatus(ACTIVE)
                        .build();
                dumpstersTosave.add(newDumpster);
            }
        }

        drDumpsterDao.saveAll(dumpstersTosave);
        List<DrDumpster> dumpstersToDelete = new ArrayList<>(actualDumpstersMap.values());
        if(!dumpstersToDelete.isEmpty()){
            dumpstersToDelete.forEach( dump -> {
                dump.setDumpstersStatus(INACTIVE);
                dump.setUpdatedBy(updater);
                dump.setUpdatedDate(now);
            });
            drDumpsterDao.saveAll(dumpstersToDelete);
        }
    }

    private void updateTools(List<DrToolCreateDto> toolsDto, DailyReport dailyReport){
        Integer dailyReportId = dailyReport.getDailyReportId();
        LocalDateTime now = LocalDateTime.now();
        String updater = dailyReport.getUpdatedBy();

        List<Tool> actualDrTools = toolDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);
        Map<Integer, Tool> actualToolsMap = actualDrTools.stream()
                .collect(Collectors.toMap(Tool::getDrToolId, Function.identity()));
        List<Tool> toolsToSave = new ArrayList<>();

        for(DrToolCreateDto dto : toolsDto){
            Integer dtoId = dto.getDrToolId();
            if(dtoId != null && actualToolsMap.containsKey(dtoId)){
                // update
                Tool drTool = actualToolsMap.get(dtoId);
                drTool.setQty(dto.getQty());
                drTool.setName(dto.getName());
                drTool.setOther(dto.getOther());
                drTool.setComments(dto.getComments());
                drTool.setUpdatedBy(updater);
                drTool.setUpdatedDate(now);
                toolsToSave.add(drTool);
                actualToolsMap.remove(dtoId);
            }else{
                // create
                Tool newDrTool = Tool.builder()
                        .dailyReportId(dailyReportId)
                        .qty(dto.getQty())
                        .name(dto.getName())
                        .other(dto.getOther())
                        .comments(dto.getComments())
                        .createdBy(updater)
                        .createdDate(now)
                        .updatedBy(updater)
                        .updatedDate(now)
                        .status(ACTIVE)
                        .build();
                toolsToSave.add(newDrTool);
            }
        }

        toolDao.saveAll(toolsToSave);
        List<Tool> toolsToDelete = new ArrayList<>(actualToolsMap.values());

        if(!toolsToDelete.isEmpty()){
            toolsToDelete.forEach(tool -> {
                tool.setStatus(INACTIVE);
                tool.setUpdatedBy(updater);
                tool.setUpdatedDate(now);
            });
            toolDao.saveAll(toolsToDelete);
        }
    }

    private void updateRentals(List<DrRentalCreateDto> rentalsDto, DailyReport dailyReport){
        Integer dailyReportId = dailyReport.getDailyReportId();
        LocalDateTime now = LocalDateTime.now();
        String updater = dailyReport.getUpdatedBy();

        List<DrRental> actualDrRentals = drRentalDao.findByDailyReportIdAndRentalsStatus(dailyReportId, ACTIVE);

        Map<Integer, DrRental> actualRentalsMap = actualDrRentals.stream()
                .collect(Collectors.toMap(DrRental::getDrRentalsId, Function.identity()));
        List<DrRental> rentalsToSave = new ArrayList<>();

        for(DrRentalCreateDto dto : rentalsDto){
            Integer dtoId = dto.getDrRentalsId();

            if(dtoId != null && actualRentalsMap.containsKey(dtoId)){
                //update
                DrRental drRental = actualRentalsMap.get(dtoId);

                drRental.setEmployeesId(dto.getEmployeesId());
                drRental.setEquipmentType(dto.getEquipmentType());
                drRental.setEquipmentName(dto.getEquipmentName());
                drRental.setCompany(dto.getCompany());
                drRental.setEquipmentNumber(dto.getEquipmentNumber());
                drRental.setOdometer(dto.getOdometer());
                drRental.setUpdatedBy(updater);
                drRental.setUpdatedDate(now);

                rentalsToSave.add(drRental);
                actualRentalsMap.remove(dtoId);
            }else{
                //create
                DrRental newRental = DrRental.builder()
                        .dailyReportId(dailyReportId)
                        .employeesId(dto.getEmployeesId())
                        .equipmentType(dto.getEquipmentType())
                        .equipmentName(dto.getEquipmentName())
                        .company(dto.getCompany())
                        .equipmentNumber(dto.getEquipmentNumber())
                        .odometer(dto.getOdometer())
                        .createdBy(updater)
                        .createdDate(now)
                        .updatedBy(updater)
                        .updatedDate(now)
                        .rentalsStatus(ACTIVE)
                        .build();

                rentalsToSave.add(newRental);
            }
        }

        drRentalDao.saveAll(rentalsToSave);
        List<DrRental> rentalsToDelete = new ArrayList<>(actualRentalsMap.values());

        if(!rentalsToDelete.isEmpty()){
            rentalsToDelete.forEach(rent -> {
                rent.setRentalsStatus(INACTIVE);
                rent.setUpdatedBy(updater);
                rent.setUpdatedDate(now);
            });

            drRentalDao.saveAll(rentalsToDelete);
        }
    }

    private void updateEquipments(List<DrEquipmentCreateDto> equipmentDto, DailyReport dailyReport){
        Integer dailyReportId = dailyReport.getDailyReportId();
        LocalDateTime now = LocalDateTime.now();
        String updater = dailyReport.getUpdatedBy();

        List<DrEquipment> actualDrEquipments = drEquipmentDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);
        Map<Integer, DrEquipment> actualEquipmentsMap = actualDrEquipments.stream()
                .collect(Collectors.toMap(DrEquipment::getDrEquipmentsId, Function.identity()));
        List<DrEquipment> equipmentsToSave = new ArrayList<>();

        for(DrEquipmentCreateDto dto : equipmentDto){
            Integer dtoId = dto.getDrEquipmentsId();
            String employeeNumber = "N/A";
            String employeeFullName = "N/A";
            String equipmentNumber = "";
            String equipmentName = "";
            String serialNumber = "";

            if(dto.getType().equals("Equipment")){
                Employee employee = employeeDao.findById(dto.getEmployeesId()).orElse(null);
                if(employee != null){
                    employeeNumber = employee.getEmployeeNumber();
                    employeeFullName = employee.getFirstName()+" "+employee.getLastName();
                }
                Equipment equipment = equipmentDao.findById(dto.getEquipmentsId()).orElse(null);
                if(equipment != null) {
                    equipmentNumber = equipment.getNumber();
                    equipmentName = equipment.getName();
                    serialNumber = equipment.getSerialNumber();
                }
            }else{
                Attachment attachment = attachmentDao.findById(dto.getEquipmentsId()).orElse(null);
                if(attachment != null) {
                    equipmentNumber = attachment.getNumber();
                    equipmentName = attachment.getName();
                    serialNumber = attachment.getSerialNumber();
                }
            }

            if(dtoId != null && actualEquipmentsMap.containsKey(dtoId)){
                DrEquipment drEquipment = actualEquipmentsMap.get(dtoId);

                drEquipment.setEmployeesId(employeeNumber);
                drEquipment.setOperator(employeeFullName);
                drEquipment.setType(dto.getType());
                drEquipment.setNumber(equipmentNumber);
                drEquipment.setName(equipmentName);
                drEquipment.setSerialNumber(serialNumber);
                drEquipment.setInitialHour(dto.getInitialHour());
                drEquipment.setNewHour(dto.getNewHour());
                drEquipment.setUpdatedBy(updater);
                drEquipment.setUpdatedDate(now);

                equipmentsToSave.add(drEquipment);
                actualEquipmentsMap.remove(dtoId);

            }else{
                DrEquipment newEquipment = DrEquipment.builder()
                        .dailyReportId(dailyReportId)
                        .equipmentsId(dto.getEquipmentsId())
                        .employeesId(employeeNumber)
                        .operator(employeeFullName)
                        .type(dto.getType())
                        .initialHour(dto.getInitialHour())
                        .newHour(dto.getNewHour())
                        .createdBy(dailyReport.getUpdatedBy())
                        .createdDate(now)
                        .updatedBy(dailyReport.getUpdatedBy())
                        .updatedDate(now)
                        .status(ACTIVE)
                        .build();

                equipmentsToSave.add(newEquipment);
            }
        }

        drEquipmentDao.saveAll(equipmentsToSave);
        List<DrEquipment> equipmentsToDelete = new ArrayList<>(actualEquipmentsMap.values());

        if(!equipmentsToDelete.isEmpty()){
            equipmentsToDelete.forEach(equip -> {
                equip.setStatus(INACTIVE);
                equip.setUpdatedBy(updater);
                equip.setUpdatedDate(now);
            });
            drEquipmentDao.saveAll(equipmentsToDelete);
        }
    }

    private void udpateEmployees(List<DrEmployeeCreateDto> employeesDto, DailyReport dailyReport){
        Integer dailyReportId = dailyReport.getDailyReportId();
        LocalDateTime now = LocalDateTime.now();
        String updater = dailyReport.getUpdatedBy();

        List<DrEmployee> actualDrEmployees = drEmployeeDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);
        Map<Integer, DrEmployee> actualEmployeesMap = actualDrEmployees.stream()
                .collect(Collectors.toMap(DrEmployee::getDrEmployeesId, Function.identity()));
        List<DrEmployee> employeesToSave = new ArrayList<>();

        for(DrEmployeeCreateDto dto : employeesDto){
            Integer dtoId = dto.getDrEmployeesId();

            if(dtoId != null && actualEmployeesMap.containsKey(dtoId)){
                DrEmployee employee = actualEmployeesMap.get(dtoId);
                employee.setInHour(dto.getInHour());
                employee.setOutHour(dto.getOutHour());
                employee.setLunch(dto.getLunch());
                employee.setPpe(dto.getPpe());
                employee.setComment(dto.getComment());
                employee.setUpdatedBy(updater);
                employee.setUpdatedDate(now);
                employeesToSave.add(employee);
                actualEmployeesMap.remove(dtoId);
            } else {
                Employee emp = employeeDao.findById(dto.getEmployeesId())
                        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
                String fullName = emp.getFirstName() + " " + emp.getLastName();
                DrEmployee newEmployee = DrEmployee.builder()
                        .dailyReportId(dailyReportId)
                        .employeesId(emp.getEmployeeNumber())
                        .name(fullName)
                        .title(emp.getTitle())
                        .inHour(dto.getInHour())
                        .outHour(dto.getOutHour())
                        .lunch(dto.getLunch())
                        .ppe(dto.getPpe())
                        .comment(dto.getComment())
                        .createdBy(updater) // Usar updater aquí si el DTO de creación no lo trae
                        .createdDate(now)
                        .updatedBy(updater)
                        .updatedDate(now)
                        .status(ACTIVE)
                        .build();
                employeesToSave.add(newEmployee);
            }
        }

        drEmployeeDao.saveAll(employeesToSave);
        List<DrEmployee> employeesToDelete = new ArrayList<>(actualEmployeesMap.values());

        if (!employeesToDelete.isEmpty()) {
            employeesToDelete.forEach(emp -> {
                emp.setStatus(INACTIVE);
                emp.setUpdatedBy(updater);
                emp.setUpdatedDate(now);
            });
            drEmployeeDao.saveAll(employeesToDelete);
        }
    }

    @Override
    public DailyReportCreateDto save(DailyReportCreateDto dailyReportCreateDto, Integer jobsId){
        Job job = jobDao.findById(jobsId)
                .orElseThrow(() -> new UsernameNotFoundException("Job not found"));

        DailyReport dailyReport = toEntity(dailyReportCreateDto, job);
        DailyReport dailyReportSaved = dailyReportDao.save(dailyReport);

        // saving crew
        List<DrEmployeeCreateDto> employeesDto = new ArrayList<>();
        if(!dailyReportCreateDto.getEmployees().isEmpty()) {
            List<DrEmployee> savedEmployees = saveEmployees(
                    dailyReportCreateDto.getEmployees(),
                    dailyReportSaved
            );
            employeesDto = savedEmployees.stream()
                            .map(this::toEmployeeDto)
                            .toList();
        }

        // saving equipment
        List<DrEquipment> savedEquipments = saveEquipments(
                dailyReportCreateDto.getEquipments(),
                dailyReportSaved
        );
        List<DrEquipmentCreateDto> equipmentsDto =
                savedEquipments.stream()
                        .map(this::toEquipmentDto)
                        .toList();

        // saving rentals
        List<DrRental> savedRentals = saveRentals(
                dailyReportCreateDto.getRentals(),
                dailyReportSaved
        );
        List<DrRentalCreateDto> rentalsDto =
                savedRentals.stream()
                        .map(this::toRentalDto)
                        .toList();

        // saving tools
        List<Tool> savedTools = saveTools(
                dailyReportCreateDto.getTools(),
                dailyReportSaved
        );
        List<DrToolCreateDto> toolsDto =
                savedTools.stream()
                        .map(this::toToolDto)
                        .toList();

        //saving dumpsters
        List<DrDumpster> savedDumpsters = saveDumpsters(
                dailyReportCreateDto.getDumpsters(),
                dailyReportSaved
        );

        List<DrDumpsterCreateDto> dumpstersDto =
                savedDumpsters.stream()
                        .map(this::toDumpsterDto)
                        .toList();

        DailyReportCreateDto response = toDto(dailyReportSaved);
        response.setEmployees(employeesDto);
        response.setEquipments(equipmentsDto);
        response.setRentals(rentalsDto);
        response.setTools(toolsDto);
        response.setDumpsters(dumpstersDto);

        return response;
    }

    private DrDumpsterCreateDto toDumpsterDto(DrDumpster drDumpster){
        return DrDumpsterCreateDto.builder()
                .drDumpstersId(drDumpster.getDrDumpstersId())
                .sourceDumpster(drDumpster.getSourceDumpster())
                .sizeDumpster(drDumpster.getSizeDumpster())
                .typeDumpster(drDumpster.getTypeDumpster())
                .quantity(drDumpster.getQuantity())
                .build();
    }

    private List<DrDumpster> saveDumpsters(List<DrDumpsterCreateDto> dumpsters, DailyReport dailyReport){
        LocalDateTime now = LocalDateTime.now();
        List<DrDumpster> result = new ArrayList<>();

        for (DrDumpsterCreateDto dto : dumpsters){
            DrDumpster dumpsterCreated = DrDumpster.builder()
                    .dailyReportId(dailyReport.getDailyReportId())
                    .sourceDumpster(dto.getSourceDumpster())
                    .sizeDumpster(dto.getSizeDumpster())
                    .typeDumpster(dto.getTypeDumpster())
                    .quantity(dto.getQuantity())
                    .createdBy(dailyReport.getUpdatedBy())
                    .createdDate(now)
                    .updatedBy(dailyReport.getUpdatedBy())
                    .updatedDate(now)
                    .dumpstersStatus(ACTIVE)
                    .build();

            result.add(drDumpsterDao.save(dumpsterCreated));
        }

        return result;
    }

    private DrRentalCreateDto toRentalDto(DrRental rental){
        return DrRentalCreateDto.builder()
                .drRentalsId(rental.getDrRentalsId())
                .employeesId(rental.getEmployeesId())
                .equipmentType(rental.getEquipmentType())
                .equipmentName(rental.getEquipmentName())
                .company(rental.getCompany())
                .equipmentNumber(rental.getEquipmentNumber())
                .odometer(rental.getOdometer())
                .build();
    }

    private List<DrRental> saveRentals(List<DrRentalCreateDto> rentals, DailyReport dailyReport){
        List<DrRental> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for(DrRentalCreateDto dto : rentals){
            DrRental rentalCreated = DrRental.builder()
                    .dailyReportId(dailyReport.getDailyReportId())
                    .employeesId(dto.getEmployeesId())
                    .equipmentType(dto.getEquipmentType())
                    .equipmentName(dto.getEquipmentName())
                    .company(dto.getCompany())
                    .equipmentNumber(dto.getEquipmentNumber())
                    .odometer(dto.getOdometer())
                    .createdBy(dailyReport.getUpdatedBy())
                    .createdDate(now)
                    .updatedBy(dailyReport.getUpdatedBy())
                    .updatedDate(now)
                    .rentalsStatus(ACTIVE)
                    .build();

            result.add(drRentalDao.save(rentalCreated));
        }

        return result;
    }

    private DrToolCreateDto toToolDto(Tool tool){

        return DrToolCreateDto.builder()
                .drToolId(tool.getDrToolId())
                .qty(tool.getQty())
                .name(tool.getName())
                .other(tool.getOther())
                .comments(tool.getComments())
                .build();
    }

    private List<Tool> saveTools(List<DrToolCreateDto> tools, DailyReport dailyReport){
        List<Tool> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for(DrToolCreateDto dto : tools){
            Tool toolCreated = Tool.builder()
                    .dailyReportId(dailyReport.getDailyReportId())
                    .qty(dto.getQty())
                    .name(dto.getName())
                    .other(dto.getOther())
                    .comments(dto.getComments())
                    .createdBy(dailyReport.getCreatedBy())
                    .createdDate(now)
                    .updatedBy(dailyReport.getCreatedBy())
                    .updatedDate(now)
                    .status(ACTIVE)
                    .build();

            result.add(toolDao.save(toolCreated));
        }

        return result;
    }

    private DrEquipmentCreateDto toEquipmentDto(DrEquipment equipment){
        Employee employee = employeeDao.findByEmployeeNumber(equipment.getEmployeesId());

        return DrEquipmentCreateDto.builder()
                .drEquipmentsId(equipment.getDrEquipmentsId())
                .equipmentsId(equipment.getEquipmentsId())
                .employeesId(employee != null ? employee.getEmployeesId() : null)
                .type(equipment.getType())
                .initialHour(equipment.getInitialHour())
                .newHour(equipment.getNewHour())
                .build();
    }

    private List<DrEquipment> saveEquipments(
            List<DrEquipmentCreateDto> equipments,
            DailyReport dailyReport
    ){
        LocalDateTime now = LocalDateTime.now();
        List<DrEquipment> savedEquipments = new ArrayList<>();

        for(DrEquipmentCreateDto dto : equipments){

            if(dto.getType().equals("Attachment")){
                DrEquipment drEquipment = DrEquipment.builder()
                        .dailyReportId(dailyReport.getDailyReportId())
                        .equipmentsId(dto.getEquipmentsId())
                        .employeesId("N/A") // this is the employee number
                        .operator("N/A")
                        .type(dto.getType())
                        .createdBy(dailyReport.getUpdatedBy())
                        .createdDate(now)
                        .updatedBy(dailyReport.getUpdatedBy())
                        .updatedDate(now)
                        .status(ACTIVE)
                        .build();

                savedEquipments.add(drEquipmentDao.save(drEquipment));
            }else{
                Employee employee = employeeDao.findById(dto.getEmployeesId())
                        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

                String fullName = employee.getFirstName() +" "+employee.getLastName();

                DrEquipment drEquipment = DrEquipment.builder()
                        .dailyReportId(dailyReport.getDailyReportId())
                        .equipmentsId(dto.getEquipmentsId())
                        .employeesId(employee.getEmployeeNumber()) // this is the employee number
                        .operator(fullName)
                        .type(dto.getType())
                        .initialHour(dto.getInitialHour())
                        .newHour(dto.getNewHour())
                        .createdBy(dailyReport.getUpdatedBy())
                        .createdDate(now)
                        .updatedBy(dailyReport.getUpdatedBy())
                        .updatedDate(now)
                        .status(ACTIVE)
                        .build();

                savedEquipments.add(drEquipmentDao.save(drEquipment));

                // save odometer
                    // in equipment table
                    // in odometer history table
            }
        }

        return savedEquipments;
    }

    private DrEmployeeCreateDto toEmployeeDto(DrEmployee emp) {
        Employee employee = employeeDao.findByEmployeeNumber(emp.getEmployeesId());

        return DrEmployeeCreateDto.builder()
                .drEmployeesId(emp.getDrEmployeesId())
                .employeesId(employee.getEmployeesId())
                .inHour(emp.getInHour())
                .outHour(emp.getOutHour())
                .lunch(emp.getLunch())
                .ppe(emp.getPpe())
                .comment(emp.getComment())
                .build();
    }

    private List<DrEmployee> saveEmployees(
            List<DrEmployeeCreateDto> employees,
            DailyReport dailyReport
    ){
        if(employees == null || employees.isEmpty()) return null;

        LocalDateTime now = LocalDateTime.now();
        List<DrEmployee> employeesList = new ArrayList<>();

        for(DrEmployeeCreateDto dto : employees){
            Employee employee = employeeDao.findById(dto.getEmployeesId())
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
            String fullName = employee.getFirstName() +" "+employee.getLastName();

            DrEmployee empCreated = DrEmployee.builder()
                    .dailyReportId(dailyReport.getDailyReportId())
                    .employeesId(employee.getEmployeeNumber()) // this is the employee number
                    .name(fullName)
                    .title(employee.getTitle())
                    .inHour(dto.getInHour())
                    .outHour(dto.getOutHour())
                    .lunch(dto.getLunch())
                    .ppe(dto.getPpe())
                    .comment(dto.getComment())
                    .createdBy(dailyReport.getUpdatedBy())
                    .createdDate(now)
                    .updatedBy(dailyReport.getUpdatedBy())
                    .updatedDate(now)
                    .status(ACTIVE)
                    .build();

            employeesList.add(drEmployeeDao.save(empCreated));
        }
        return employeesList;
    }

    private DailyReportCreateDto toDto(DailyReport report) {
        return DailyReportCreateDto.builder()
                .dailyReportId(report.getDailyReportId())
                .foreman(report.getForeman())
                .userName(report.getUpdatedBy())
                .date(report.getDate())
                .description(report.getDescription())
                .manOther(report.getManOther())
                .equipmentOther(report.getEquipmentOther())
                .issues(report.getIssues())
                .employees(null)
                .build();
    }

    private DailyReport toEntity(
            DailyReportCreateDto dailyReportCreateDto,
            Job job
    ){
        LocalDateTime now = LocalDateTime.now();
        return DailyReport.builder()
                .number(job.getNumber())
                .address(job.getAddress())
                .name(job.getName())
                .workingFor(job.getContractor())
                .date(dailyReportCreateDto.getDate())
                .foreman(dailyReportCreateDto.getForeman())
                .description(dailyReportCreateDto.getDescription())
                .manOther(dailyReportCreateDto.getManOther())
                .equipmentOther(dailyReportCreateDto.getEquipmentOther())
                .issues(dailyReportCreateDto.getIssues())
                .createdBy(dailyReportCreateDto.getUserName())
                .createdDate(now)
                .updatedBy(dailyReportCreateDto.getUserName())
                .updatedDate(now)
                .status(ACTIVE)
                .build();
    }

    @Override
    public List<DailyReport> findByNumber(String number){
        return (List<DailyReport>) dailyReportDao.findByNumberAndStatus(number, "1");
    }

    @Override
    public List<DailyReport> findByNumbers(List<String> numbers){
        return (List<DailyReport>) dailyReportDao.findByNumberIn(numbers);
    }

    @Override
    public DailyReport findById(Integer id){
        return dailyReportDao.findById(id).orElse(null);
    }

    @Override
    public DailyReport findByNumberAndDate(String number, LocalDate date){
        return dailyReportDao.findByNumberAndDate(number, date);
    }

    @Override
    public List<DailyReport> findAll() {
        return (List<DailyReport>) dailyReportDao.findAll();
    }

    @Override
    public List<DailyReportSummaryDto> findSummaryByJobNumber(String jobNumber){
        return (List<DailyReportSummaryDto>) dailyReportDao.findSummaryByJobNumber(jobNumber);
    }

    @Override
    public Integer findTotalDaysByJobNumber(String jobNumber, LocalDate date){
        return dailyReportDao.getTotalDaysByJobNumber(jobNumber, date);
    }


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DailyReportGralDto> getDrGral(String reportNumber) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("GetDailyReportGral");

        query.registerStoredProcedureParameter("report_number", String.class, ParameterMode.IN);
        query.setParameter("report_number", reportNumber);

        List<Object[]> results = query.getResultList();

        // 🔹 Mapear manualmente a DTO
        return results.stream()
                .map(r -> new DailyReportGralDto(
                        ((Number) r[0]).intValue(),
                        (Date) r[1],
                        (String) r[2],
                        (String) r[3],
                        (String) r[4],
                        ((Number) r[5]).intValue(),
                        ((Number) r[6]).intValue(),
                        ((Number) r[7]).intValue()

                ))
                .toList();
    }
}
