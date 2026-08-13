package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.AttachmentDao;
import com.ck.wi.model.dao.EmployeeDao;
import com.ck.wi.model.dao.EquipmentDao;
import com.ck.wi.model.dao.JobDao;
import com.ck.wi.model.dao.dailyReport.*;
import com.ck.wi.model.dto.dailyReport.DailyReportDto;
import com.ck.wi.model.dto.dailyReport.DailyReportGralDto;
import com.ck.wi.model.dto.dailyReport.DailyReportSummaryDto;
import com.ck.wi.model.dto.dailyReport.EmployeeHoursDTO;
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
        dailyReportToUpdate.setManTotal(String.valueOf(dailyReportCreateDto.getEmployees().size()));
        dailyReportToUpdate.setEquipmentTotal(String.valueOf(dailyReportCreateDto.getEquipments().size()));
        dailyReportToUpdate.setManOther(dailyReportCreateDto.getManOther());
        dailyReportToUpdate.setEquipmentOther(dailyReportCreateDto.getEquipmentOther());
        dailyReportToUpdate.setIssues(dailyReportCreateDto.getIssues());
        dailyReportToUpdate.setUpdatedBy(dailyReportCreateDto.getUserName());
        dailyReportToUpdate.setUpdatedDate(now);

        DailyReport dailyReportUpdated = dailyReportDao.save(dailyReportToUpdate);
        updateEmployees(dailyReportCreateDto.getEmployees(), dailyReportUpdated);
        updateEquipments(dailyReportCreateDto.getEquipments(), dailyReportUpdated);
        updateRentals(dailyReportCreateDto.getRentals(), dailyReportUpdated);
        updateTools(dailyReportCreateDto.getTools(), dailyReportUpdated);
        updateDumpsters(dailyReportCreateDto.getDumpsters(), dailyReportUpdated);

        DailyReportCreateDto response = toDto(dailyReportUpdated);
        return allDto(response, dailyReportCreateDto.getDailyReportId());
    }

    private DailyReportCreateDto allDto(DailyReportCreateDto response, Integer dailyReportId) {
        List<DrEmployee> actualDrEmployees = drEmployeeDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);

        // Carga rápida en lote para mapear employeeId
        List<String> empNumbers = actualDrEmployees.stream().map(DrEmployee::getEmployeesId).toList();
        // En caso de que se requiera convertir o buscar
        Map<String, Integer> empNumberToIdMap = empNumbers.isEmpty() ? Collections.emptyMap() :
                employeeDao.findAllById(
                        actualDrEmployees.stream().map(DrEmployee::getDrEmployeesId).toList()
                ).stream().collect(Collectors.toMap(Employee::getEmployeeNumber, Employee::getEmployeesId, (k1, k2) -> k1));

        List<DrEmployeeCreateDto> employeesDto = actualDrEmployees.stream()
                .map(this::toEmployeeDto)
                .toList();

        List<DrEquipment> actualDrEquipments = drEquipmentDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);
        List<DrEquipmentCreateDto> equipmentsDto = actualDrEquipments.stream()
                .map(this::toEquipmentDto)
                .toList();

        List<DrRental> actualDrRentals = drRentalDao.findByDailyReportIdAndRentalsStatus(dailyReportId, ACTIVE);
        List<DrRentalCreateDto> rentalsDto = actualDrRentals.stream()
                .map(this::toRentalDto)
                .toList();

        List<Tool> actualDrTools = toolDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);
        List<DrToolCreateDto> toolsDto = actualDrTools.stream()
                .map(this::toToolDto)
                .toList();

        List<DrDumpster> actualDrDumpsters = drDumpsterDao.findByDailyReportIdAndDumpstersStatus(dailyReportId, ACTIVE);
        List<DrDumpsterCreateDto> dumpstersDto = actualDrDumpsters.stream()
                .map(this::toDumpsterDto)
                .toList();

        response.setEmployees(employeesDto);
        response.setEquipments(equipmentsDto);
        response.setRentals(rentalsDto);
        response.setTools(toolsDto);
        response.setDumpsters(dumpstersDto);

        return response;
    }

    private DrEmployeeCreateDto toEmployeeDto(DrEmployee emp) {
        return DrEmployeeCreateDto.builder()
                .drEmployeesId(emp.getDrEmployeesId())
                .inHour(emp.getInHour())
                .outHour(emp.getOutHour())
                .lunch(emp.getLunch())
                .ppe(emp.getPpe())
                .comment(emp.getComment())
                .build();
    }

    private DrEquipmentCreateDto toEquipmentDto(DrEquipment equipment) {
        return DrEquipmentCreateDto.builder()
                .drEquipmentsId(equipment.getDrEquipmentsId())
                .equipmentsId(equipment.getEquipmentsId())
                .type(equipment.getType())
                .initialHour(equipment.getInitialHour())
                .newHour(equipment.getNewHour())
                .build();
    }

    private void updateDumpsters(List<DrDumpsterCreateDto> dummpstersDto, DailyReport dailyReport) {
        if (dummpstersDto == null) return;

        Integer dailyReportId = dailyReport.getDailyReportId();
        LocalDateTime now = LocalDateTime.now();
        String updater = dailyReport.getUpdatedBy();

        List<DrDumpster> actualDrDumpsters = drDumpsterDao.findByDailyReportIdAndDumpstersStatus(dailyReportId, ACTIVE);
        Map<Integer, DrDumpster> actualDumpstersMap = actualDrDumpsters.stream()
                .collect(Collectors.toMap(DrDumpster::getDrDumpstersId, Function.identity()));

        List<DrDumpster> dumpstersTosave = new ArrayList<>();

        for (DrDumpsterCreateDto dto : dummpstersDto) {
            Integer dtoId = dto.getDrDumpstersId();

            if (dtoId != null && actualDumpstersMap.containsKey(dtoId)) {
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
            } else {
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

        // SOFT DELETE
        List<DrDumpster> dumpstersToDelete = new ArrayList<>(actualDumpstersMap.values());
        if (!dumpstersToDelete.isEmpty()) {
            dumpstersToDelete.forEach(dump -> {
                dump.setDumpstersStatus(INACTIVE);
                dump.setUpdatedBy(updater);
                dump.setUpdatedDate(now);
            });
            drDumpsterDao.saveAll(dumpstersToDelete);
        }
    }

    private void updateTools(List<DrToolCreateDto> toolsDto, DailyReport dailyReport) {
        if (toolsDto == null) return;

        Integer dailyReportId = dailyReport.getDailyReportId();
        LocalDateTime now = LocalDateTime.now();
        String updater = dailyReport.getUpdatedBy();

        List<Tool> actualDrTools = toolDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);
        Map<Integer, Tool> actualToolsMap = actualDrTools.stream()
                .collect(Collectors.toMap(Tool::getDrToolId, Function.identity()));

        List<Tool> toolsToSave = new ArrayList<>();

        for (DrToolCreateDto dto : toolsDto) {
            Integer dtoId = dto.getDrToolId();

            if (dtoId != null && actualToolsMap.containsKey(dtoId)) {
                Tool drTool = actualToolsMap.get(dtoId);
                drTool.setQty(dto.getQty());
                drTool.setName(dto.getName());
                drTool.setOther(dto.getOther());
                drTool.setComments(dto.getComments());
                drTool.setUpdatedBy(updater);
                drTool.setUpdatedDate(now);

                toolsToSave.add(drTool);
                actualToolsMap.remove(dtoId);
            } else {
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

        // SOFT DELETE
        List<Tool> toolsToDelete = new ArrayList<>(actualToolsMap.values());
        if (!toolsToDelete.isEmpty()) {
            toolsToDelete.forEach(tool -> {
                tool.setStatus(INACTIVE);
                tool.setUpdatedBy(updater);
                tool.setUpdatedDate(now);
            });
            toolDao.saveAll(toolsToDelete);
        }
    }

    private void updateRentals(List<DrRentalCreateDto> rentalsDto, DailyReport dailyReport) {
        if (rentalsDto == null) return;

        Integer dailyReportId = dailyReport.getDailyReportId();
        LocalDateTime now = LocalDateTime.now();
        String updater = dailyReport.getUpdatedBy();

        List<DrRental> actualDrRentals = drRentalDao.findByDailyReportIdAndRentalsStatus(dailyReportId, ACTIVE);
        Map<Integer, DrRental> actualRentalsMap = actualDrRentals.stream()
                .collect(Collectors.toMap(DrRental::getDrRentalsId, Function.identity()));

        List<DrRental> rentalsToSave = new ArrayList<>();

        for (DrRentalCreateDto dto : rentalsDto) {
            Integer dtoId = dto.getDrRentalsId();

            if (dtoId != null && actualRentalsMap.containsKey(dtoId)) {
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
            } else {
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

        // SOFT DELETE
        List<DrRental> rentalsToDelete = new ArrayList<>(actualRentalsMap.values());
        if (!rentalsToDelete.isEmpty()) {
            rentalsToDelete.forEach(rent -> {
                rent.setRentalsStatus(INACTIVE);
                rent.setUpdatedBy(updater);
                rent.setUpdatedDate(now);
            });
            drRentalDao.saveAll(rentalsToDelete);
        }
    }

    private void updateEquipments(List<DrEquipmentCreateDto> equipmentDto, DailyReport dailyReport) {
        if (equipmentDto == null) return;

        Integer dailyReportId = dailyReport.getDailyReportId();
        LocalDateTime now = LocalDateTime.now();
        String updater = dailyReport.getUpdatedBy();

        List<DrEquipment> actualDrEquipments = drEquipmentDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);
        Map<Integer, DrEquipment> actualEquipmentsMap = actualDrEquipments.stream()
                .collect(Collectors.toMap(DrEquipment::getDrEquipmentsId, Function.identity()));

        // ✅ BATCH LOAD: Cargar Empleados, Equipos y Attachments en lote antes del ciclo
        List<Integer> empIds = equipmentDto.stream()
                .filter(dto -> "Equipment".equals(dto.getType()) && dto.getEmployeesId() != null)
                .map(DrEquipmentCreateDto::getEmployeesId)
                .toList();
        Map<Integer, Employee> empMap = empIds.isEmpty() ? Collections.emptyMap() :
                employeeDao.findAllById(empIds).stream().collect(Collectors.toMap(Employee::getEmployeesId, Function.identity()));

        List<Integer> eqIds = equipmentDto.stream()
                .filter(dto -> "Equipment".equals(dto.getType()) && dto.getEquipmentsId() != null)
                .map(DrEquipmentCreateDto::getEquipmentsId)
                .toList();
        Map<Integer, Equipment> eqMap = eqIds.isEmpty() ? Collections.emptyMap() :
                equipmentDao.findAllById(eqIds).stream().collect(Collectors.toMap(Equipment::getEquipmentsId, Function.identity()));

        List<Integer> attachIds = equipmentDto.stream()
                .filter(dto -> !"Equipment".equals(dto.getType()) && dto.getEquipmentsId() != null)
                .map(DrEquipmentCreateDto::getEquipmentsId)
                .toList();
        Map<Integer, Attachment> attachMap = attachIds.isEmpty() ? Collections.emptyMap() :
                attachmentDao.findAllById(attachIds).stream().collect(Collectors.toMap(Attachment::getAttachmentsId, Function.identity()));

        List<DrEquipment> equipmentsToSave = new ArrayList<>();

        for (DrEquipmentCreateDto dto : equipmentDto) {
            Integer dtoId = dto.getDrEquipmentsId();
            String employeeNumber = "N/A";
            String employeeFullName = "N/A";
            String equipmentNumber = "";
            String equipmentName = "";
            String serialNumber = "";

            if ("Equipment".equals(dto.getType())) {
                Employee employee = empMap.get(dto.getEmployeesId());
                if (employee != null) {
                    employeeNumber = employee.getEmployeeNumber();
                    employeeFullName = employee.getFirstName() + " " + employee.getLastName();
                }
                Equipment equipment = eqMap.get(dto.getEquipmentsId());
                if (equipment != null) {
                    equipmentNumber = equipment.getNumber();
                    equipmentName = equipment.getName();
                    serialNumber = equipment.getSerialNumber();
                }
            } else {
                Attachment attachment = attachMap.get(dto.getEquipmentsId());
                if (attachment != null) {
                    equipmentNumber = attachment.getNumber();
                    equipmentName = attachment.getName();
                    serialNumber = attachment.getSerialNumber();
                }
            }

            if (dtoId != null && actualEquipmentsMap.containsKey(dtoId)) {
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
            } else {
                DrEquipment newEquipment = DrEquipment.builder()
                        .dailyReportId(dailyReportId)
                        .equipmentsId(dto.getEquipmentsId())
                        .employeesId(employeeNumber)
                        .operator(employeeFullName)
                        .type(dto.getType())
                        .number(equipmentNumber)
                        .name(equipmentName)
                        .serialNumber(serialNumber)
                        .initialHour(dto.getInitialHour())
                        .newHour(dto.getNewHour())
                        .createdBy(updater)
                        .createdDate(now)
                        .updatedBy(updater)
                        .updatedDate(now)
                        .status(ACTIVE)
                        .build();

                equipmentsToSave.add(newEquipment);
            }
        }

        drEquipmentDao.saveAll(equipmentsToSave);

        // SOFT DELETE
        List<DrEquipment> equipmentsToDelete = new ArrayList<>(actualEquipmentsMap.values());
        if (!equipmentsToDelete.isEmpty()) {
            equipmentsToDelete.forEach(equip -> {
                equip.setStatus(INACTIVE);
                equip.setUpdatedBy(updater);
                equip.setUpdatedDate(now);
            });
            drEquipmentDao.saveAll(equipmentsToDelete);
        }
    }

    private void updateEmployees(List<DrEmployeeCreateDto> employeesDto, DailyReport dailyReport) {
        if (employeesDto == null) return;

        Integer dailyReportId = dailyReport.getDailyReportId();
        LocalDateTime now = LocalDateTime.now();
        String updater = dailyReport.getUpdatedBy();

        List<DrEmployee> actualDrEmployees = drEmployeeDao.findByDailyReportIdAndStatus(dailyReportId, ACTIVE);
        Map<Integer, DrEmployee> actualEmployeesMap = actualDrEmployees.stream()
                .collect(Collectors.toMap(DrEmployee::getDrEmployeesId, Function.identity()));

        List<DrEmployee> employeesToSave = new ArrayList<>();

        // ✅ BATCH LOAD: Cargar empleados nuevos en lote antes del for para evitar N+1
        List<Integer> newEmpIds = employeesDto.stream()
                .filter(dto -> dto.getDrEmployeesId() == null || !actualEmployeesMap.containsKey(dto.getDrEmployeesId()))
                .map(DrEmployeeCreateDto::getEmployeesId)
                .filter(Objects::nonNull)
                .toList();

        Map<Integer, Employee> newEmployeesMap = newEmpIds.isEmpty() ? Collections.emptyMap() :
                employeeDao.findAllById(newEmpIds).stream()
                        .collect(Collectors.toMap(Employee::getEmployeesId, Function.identity()));

        for (DrEmployeeCreateDto dto : employeesDto) {
            Integer dtoId = dto.getDrEmployeesId();

            if (dtoId != null && actualEmployeesMap.containsKey(dtoId)) {
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
                Employee emp = newEmployeesMap.get(dto.getEmployeesId());
                if (emp == null) {
                    throw new IllegalArgumentException("Employee not found ID: " + dto.getEmployeesId());
                }
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
                        .createdBy(updater)
                        .createdDate(now)
                        .updatedBy(updater)
                        .updatedDate(now)
                        .status(ACTIVE)
                        .build();
                employeesToSave.add(newEmployee);
            }
        }

        drEmployeeDao.saveAll(employeesToSave);

        // SOFT DELETE para los registros removidos
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
    @Transactional // ✅ Una sola transacción para toda la operación
    public DailyReportCreateDto save(DailyReportCreateDto dailyReportCreateDto, Integer jobsId) {
        Job job = jobDao.findById(jobsId)
                .orElseThrow(() -> new UsernameNotFoundException("Job not found"));

        DailyReport dailyReport = toEntity(dailyReportCreateDto, job);
        DailyReport dailyReportSaved = dailyReportDao.save(dailyReport);

        // 1. Crew / Employees
        List<DrEmployeeCreateDto> employeesDto = new ArrayList<>();
        if (dailyReportCreateDto.getEmployees() != null && !dailyReportCreateDto.getEmployees().isEmpty()) {
            List<DrEmployee> savedEmployees = saveEmployees(dailyReportCreateDto.getEmployees(), dailyReportSaved);

            // Map sin hacer queries extra a la BD
            Map<String, Integer> empNumberToIdMap = employeeDao.findAllById(
                    dailyReportCreateDto.getEmployees().stream().map(DrEmployeeCreateDto::getEmployeesId).toList()
            ).stream().collect(Collectors.toMap(Employee::getEmployeeNumber, Employee::getEmployeesId));

            employeesDto = savedEmployees.stream()
                    .map(emp -> toEmployeeDtoOptimized(emp, empNumberToIdMap.get(emp.getEmployeesId())))
                    .toList();
        }

        // 2. Equipment
        List<DrEquipment> savedEquipments = saveEquipments(dailyReportCreateDto.getEquipments(), dailyReportSaved);
        List<DrEquipmentCreateDto> equipmentsDto = savedEquipments.stream()
                .map(this::toEquipmentDtoOptimized)
                .toList();

        // 3. Rentals
        List<DrRental> savedRentals = saveRentals(dailyReportCreateDto.getRentals(), dailyReportSaved);
        List<DrRentalCreateDto> rentalsDto = savedRentals.stream()
                .map(this::toRentalDto)
                .toList();

        // 4. Tools
        List<Tool> savedTools = saveTools(dailyReportCreateDto.getTools(), dailyReportSaved);
        List<DrToolCreateDto> toolsDto = savedTools.stream()
                .map(this::toToolDto)
                .toList();

        // 5. Dumpsters
        List<DrDumpster> savedDumpsters = saveDumpsters(dailyReportCreateDto.getDumpsters(), dailyReportSaved);
        List<DrDumpsterCreateDto> dumpstersDto = savedDumpsters.stream()
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

    private List<DrDumpster> saveDumpsters(List<DrDumpsterCreateDto> dumpsters, DailyReport dailyReport) {
        if (dumpsters == null || dumpsters.isEmpty()) return Collections.emptyList();
        LocalDateTime now = LocalDateTime.now();

        List<DrDumpster> toSave = dumpsters.stream().map(dto -> DrDumpster.builder()
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
                .build()
        ).toList();

        return drDumpsterDao.saveAll(toSave); // ✅ Batch insert
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

    private List<DrRental> saveRentals(List<DrRentalCreateDto> rentals, DailyReport dailyReport) {
        if (rentals == null || rentals.isEmpty()) return Collections.emptyList();
        LocalDateTime now = LocalDateTime.now();

        List<DrRental> toSave = rentals.stream().map(dto -> DrRental.builder()
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
                .build()
        ).toList();

        return drRentalDao.saveAll(toSave); // ✅ Batch insert
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

    private List<Tool> saveTools(List<DrToolCreateDto> tools, DailyReport dailyReport) {
        if (tools == null || tools.isEmpty()) return Collections.emptyList();
        LocalDateTime now = LocalDateTime.now();

        List<Tool> toSave = tools.stream().map(dto -> Tool.builder()
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
                .build()
        ).toList();

        return toolDao.saveAll(toSave); // ✅ Batch insert
    }

    private List<DrEquipment> saveEquipments(List<DrEquipmentCreateDto> equipments, DailyReport dailyReport) {
        if (equipments == null || equipments.isEmpty()) return Collections.emptyList();

        LocalDateTime now = LocalDateTime.now();
        List<DrEquipment> toSave = new ArrayList<>();
        List<Equipment> equipmentToUpdate = new ArrayList<>();

        // Buscar empleados en lote si los hay
        List<Integer> empIds = equipments.stream()
                .filter(e -> !"Attachment".equals(e.getType()) && e.getEmployeesId() != null)
                .map(DrEquipmentCreateDto::getEmployeesId)
                .toList();

        Map<Integer, Employee> empMap = empIds.isEmpty() ? Collections.emptyMap() :
                employeeDao.findAllById(empIds).stream().collect(Collectors.toMap(Employee::getEmployeesId, e -> e));

        // Buscar equipos a actualizar horas en lote
        List<Integer> eqIds = equipments.stream().map(DrEquipmentCreateDto::getEquipmentsId).toList();
        Map<Integer, Equipment> eqMap = equipmentDao.findAllById(eqIds).stream()
                .collect(Collectors.toMap(Equipment::getEquipmentsId, e -> e));

        for (DrEquipmentCreateDto dto : equipments) {
            String empNumber = "N/A";
            String fullName = "N/A";

            if (!"Attachment".equals(dto.getType())) {
                Employee employee = empMap.get(dto.getEmployeesId());
                if (employee != null) {
                    empNumber = employee.getEmployeeNumber();
                    fullName = employee.getFirstName() + " " + employee.getLastName();
                }

                // Actualizar horas de equipo
                Equipment eq = eqMap.get(dto.getEquipmentsId());
                if (eq != null && dto.getNewHour() != null) {
                    eq.setHour(Float.parseFloat(dto.getNewHour()));
                    equipmentToUpdate.add(eq);
                }
            }

            toSave.add(DrEquipment.builder()
                    .dailyReportId(dailyReport.getDailyReportId())
                    .equipmentsId(dto.getEquipmentsId())
                    .employeesId(empNumber)
                    .operator(fullName)
                    .type(dto.getType())
                    .initialHour(dto.getInitialHour())
                    .newHour(dto.getNewHour())
                    .createdBy(dailyReport.getUpdatedBy())
                    .createdDate(now)
                    .updatedBy(dailyReport.getUpdatedBy())
                    .updatedDate(now)
                    .status(ACTIVE)
                    .build());
        }

        if (!equipmentToUpdate.isEmpty()) {
            equipmentDao.saveAll(equipmentToUpdate); // ✅ Batch update
        }

        return drEquipmentDao.saveAll(toSave); // ✅ Batch insert
    }

    private DrEquipmentCreateDto toEquipmentDtoOptimized(DrEquipment equipment) {
        // No necesita hacer consulta a la BD ya que devuelve lo que ya se guardó
        return DrEquipmentCreateDto.builder()
                .drEquipmentsId(equipment.getDrEquipmentsId())
                .equipmentsId(equipment.getEquipmentsId())
                .type(equipment.getType())
                .initialHour(equipment.getInitialHour())
                .newHour(equipment.getNewHour())
                .build();
    }

    private DrEmployeeCreateDto toEmployeeDtoOptimized(DrEmployee emp, Integer employeeId) {
        return DrEmployeeCreateDto.builder()
                .drEmployeesId(emp.getDrEmployeesId())
                .employeesId(employeeId)
                .inHour(emp.getInHour())
                .outHour(emp.getOutHour())
                .lunch(emp.getLunch())
                .ppe(emp.getPpe())
                .comment(emp.getComment())
                .build();
    }

    private List<DrEmployee> saveEmployees(List<DrEmployeeCreateDto> employees, DailyReport dailyReport) {
        if (employees == null || employees.isEmpty()) return Collections.emptyList();

        LocalDateTime now = LocalDateTime.now();

        // Cargar todos los empleados en UNA SOLA QUERY en vez de dentro del loop
        List<Integer> employeeIds = employees.stream().map(DrEmployeeCreateDto::getEmployeesId).toList();
        Map<Integer, Employee> employeeMap = employeeDao.findAllById(employeeIds).stream()
                .collect(Collectors.toMap(Employee::getEmployeesId, e -> e));

        List<DrEmployee> toSave = new ArrayList<>();
        for (DrEmployeeCreateDto dto : employees) {
            Employee employee = employeeMap.get(dto.getEmployeesId());
            if (employee == null) {
                throw new IllegalArgumentException("Employee not found ID: " + dto.getEmployeesId());
            }

            toSave.add(DrEmployee.builder()
                    .dailyReportId(dailyReport.getDailyReportId())
                    .employeesId(employee.getEmployeeNumber())
                    .name(employee.getFirstName() + " " + employee.getLastName())
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
                    .build());
        }
        // ✅ 1 sola instruccion INSERT batch a la BD
        return drEmployeeDao.saveAll(toSave);
    }

    private DailyReportCreateDto toDto(DailyReport report) {
        Job job = jobDao.findByNumber(report.getNumber())
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));
        return DailyReportCreateDto.builder()
                .dailyReportId(report.getDailyReportId())
                .jobsId(job.getJobsId())
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
                .manTotal(String.valueOf(dailyReportCreateDto.getEmployees().size()))
                .equipmentTotal(String.valueOf(dailyReportCreateDto.getEquipments().size()))
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

    @Override
    public List<EmployeeHoursDTO> getHoursByDate(LocalDate start, LocalDate end) {
        return dailyReportDao.findEmployeeHoursSummary(start.toString(), end.toString());
    }
}
