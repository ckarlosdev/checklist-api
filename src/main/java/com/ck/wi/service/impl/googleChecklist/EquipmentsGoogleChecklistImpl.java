package com.ck.wi.service.impl.googleChecklist;

import com.ck.wi.model.dao.googleChecklist.EquipmentsGoogleChecklistDao;
import com.ck.wi.model.dao.googleChecklist.GoogleChecklistDao;
import com.ck.wi.model.dto.googleChecklist.EquipmentsGoogleChecklistCreateDto;
import com.ck.wi.model.dto.googleChecklist.GoogleCheckListCreateDto;
import com.ck.wi.model.entity.dailyReport.DrEmployee;
import com.ck.wi.model.entity.googleChecklist.EquipmentsGoogleChecklist;
import com.ck.wi.model.entity.googleChecklist.GoogleChecklist;
import com.ck.wi.service.googleChecklist.IEquipmentsGoogleChecklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EquipmentsGoogleChecklistImpl implements IEquipmentsGoogleChecklist {

    @Autowired
    private EquipmentsGoogleChecklistDao equipmentsGoogleChecklistDao;

    @Autowired
    private GoogleChecklistDao googleChecklistDao;

    @Override
    public EquipmentsGoogleChecklist findById(Integer id){
        return equipmentsGoogleChecklistDao.findById(id).orElse(null);
    }

    @Override
    public EquipmentsGoogleChecklist findByJobsIdAndDate(Integer jobsId, LocalDate date){
        return equipmentsGoogleChecklistDao.findByJobsIdAndDate(jobsId, date);
    }

    private static final String ACTIVE = "1";
    private static final String INACTIVE = "0";

    @Override
    public Integer save(EquipmentsGoogleChecklistCreateDto checklistCreateDto){
        if(checklistCreateDto.getJobsId() == null || checklistCreateDto.getJobsId() == 0){
            throw new IllegalArgumentException("El ID de Job no puede ser nulo o cero");
        }

        LocalDateTime now = LocalDateTime.now();
        EquipmentsGoogleChecklist checklist = cltoEntity(checklistCreateDto, now);
        EquipmentsGoogleChecklist checklistSaved = equipmentsGoogleChecklistDao.save(checklist);
        saveChecklists( checklistCreateDto.getChecklists(), checklistSaved, now);

        return checklistSaved.getEquipmentsGoogleChecklistsId();
    }

    public void saveChecklists(
            List<GoogleCheckListCreateDto> checklistsDtos,
            EquipmentsGoogleChecklist clSaved,
            LocalDateTime now)
    {
        for(GoogleCheckListCreateDto dto : checklistsDtos){
            GoogleChecklist newcl =
                    GoogleChecklist.builder()
                            .equipmentsGoogleChecklistsId(clSaved.getEquipmentsGoogleChecklistsId())
                            .equipmentNumber(dto.getEquipmentNumber())
                            .equipmentName(dto.getEquipmentName())
                            .operator(dto.getOperator())
                            .odometer(dto.getOdometer())
                            .oil(dto.getOil())
                            .hydraulic(dto.getHydraulic())
                            .filter(dto.getFilter())
                            .radiator(dto.getRadiator())
                            .track(dto.getTrack())
                            .attachment(dto.getAttachment())
                            .leaking(dto.getLeaking())
                            .diesel(dto.getDiesel())
                            .clean(dto.getClean())
                            .comment(dto.getComment())
                            .createdBy(clSaved.getUpdatedBy())
                            .createdDate(now)
                            .updatedBy(clSaved.getUpdatedBy())
                            .updatedDate(now)
                            .status(ACTIVE)
                            .otherType(dto.getOtherType())
                            .build();

            googleChecklistDao.save(newcl);

        }
    }

    private EquipmentsGoogleChecklist cltoEntity(
            EquipmentsGoogleChecklistCreateDto checklistCreateDto,
            LocalDateTime now
    ){
        return EquipmentsGoogleChecklist.builder()
                .equipmentsGoogleChecklistsId(null)
                .jobsId(checklistCreateDto.getJobsId())
                .total(checklistCreateDto.getChecklists() != null ? checklistCreateDto.getChecklists().size() : 0)
                .date(checklistCreateDto.getDate())
                .createdBy(checklistCreateDto.getUserName())
                .createdDate(now)
                .updatedBy(checklistCreateDto.getUserName())
                .updatedDate(now)
                .status(ACTIVE)
                .token("")
                .build();
    }

    @Override
    public void update(EquipmentsGoogleChecklistCreateDto checklistCreateDto){
        EquipmentsGoogleChecklist checklistToUpdate =
                equipmentsGoogleChecklistDao.findById(checklistCreateDto.getEquipmentsGoogleChecklistsId())
                        .orElseThrow(() -> new IllegalArgumentException("Equipment Checklist not found."));

        LocalDateTime now = LocalDateTime.now();

        checklistToUpdate.setTotal(checklistCreateDto.getChecklists().size());
        checklistToUpdate.setDate(checklistCreateDto.getDate());
        checklistToUpdate.setUpdatedBy(checklistCreateDto.getUserName());
        checklistToUpdate.setUpdatedDate(now);

        EquipmentsGoogleChecklist checklistUpdated = equipmentsGoogleChecklistDao.save(checklistToUpdate);

        updateCl(checklistCreateDto.getChecklists(), now, checklistUpdated);
    }

    private void updateCl(
        List<GoogleCheckListCreateDto> checklistsDto,
        LocalDateTime now,
        EquipmentsGoogleChecklist checklistUpdated
    ){
        List<GoogleChecklist> actualChecklists =
                googleChecklistDao.findByEquipmentsGoogleChecklistsId(
                        checklistUpdated.getEquipmentsGoogleChecklistsId()
                );
        Map<Integer, GoogleChecklist> actualChecklistsMap =
                actualChecklists.stream()
                        .collect(Collectors.toMap(GoogleChecklist::getGoogleChecklistsId, Function.identity()));

        List<GoogleChecklist> checklistsToSave = new ArrayList<>();

        for(GoogleCheckListCreateDto dto : checklistsDto){
            Integer dtoId = dto.getGoogleChecklistsId();

            if(dtoId != null && actualChecklistsMap.containsKey(dtoId)){
                GoogleChecklist checklistToSave = actualChecklistsMap.get(dtoId);
                checklistToSave.setEquipmentsGoogleChecklistsId(checklistUpdated.getEquipmentsGoogleChecklistsId());
                checklistToSave.setEquipmentNumber(dto.getEquipmentNumber());
                checklistToSave.setEquipmentName(dto.getEquipmentName());
                checklistToSave.setOperator(dto.getOperator());
                checklistToSave.setOdometer(dto.getOdometer());
                checklistToSave.setOil(dto.getOil());
                checklistToSave.setHydraulic(dto.getHydraulic());
                checklistToSave.setFilter(dto.getFilter());
                checklistToSave.setRadiator(dto.getRadiator());
                checklistToSave.setTrack(dto.getTrack());
                checklistToSave.setAttachment(dto.getAttachment());
                checklistToSave.setLeaking(dto.getLeaking());
                checklistToSave.setDiesel(dto.getDiesel());
                checklistToSave.setClean(dto.getClean());
                checklistToSave.setComment(dto.getComment());
                checklistToSave.setUpdatedBy(checklistUpdated.getUpdatedBy());
                checklistToSave.setUpdatedDate(now);
                checklistToSave.setOtherType(dto.getOtherType());

                checklistsToSave.add(checklistToSave);
                actualChecklistsMap.remove(dtoId);
            }else{
                GoogleChecklist newChecklist =
                        GoogleChecklist.builder()
                                .equipmentsGoogleChecklistsId(checklistUpdated.getEquipmentsGoogleChecklistsId())
                                .equipmentNumber(dto.getEquipmentNumber())
                                .equipmentName(dto.getEquipmentName())
                                .operator(dto.getOperator())
                                .odometer(dto.getOdometer())
                                .oil(dto.getOil())
                                .hydraulic(dto.getHydraulic())
                                .filter(dto.getFilter())
                                .radiator(dto.getRadiator())
                                .track(dto.getTrack())
                                .attachment(dto.getAttachment())
                                .leaking(dto.getLeaking())
                                .diesel(dto.getDiesel())
                                .clean(dto.getClean())
                                .comment(dto.getComment())
                                .createdBy(checklistUpdated.getUpdatedBy())
                                .createdDate(now)
                                .updatedBy(checklistUpdated.getUpdatedBy())
                                .updatedDate(now)
                                .status(ACTIVE)
                                .otherType(dto.getOtherType())
                                .build();
                checklistsToSave.add(newChecklist);
            }
        }

        googleChecklistDao.saveAll(checklistsToSave);
        List<GoogleChecklist> checklistToDelete = new ArrayList<>(actualChecklistsMap.values());

        if( !checklistToDelete.isEmpty() ){
            checklistToDelete.forEach(cl -> {
                cl.setStatus(INACTIVE);
                cl.setUpdatedBy(checklistUpdated.getUpdatedBy());
                cl.setUpdatedDate(now);
            });
            googleChecklistDao.saveAll(checklistToDelete);
        }
    }

    @Override
    public List<EquipmentsGoogleChecklist> getAllChecklist(Integer jobId){
        return  equipmentsGoogleChecklistDao.findAllByJobId(jobId);
    }

    @Override
    public EquipmentsGoogleChecklistCreateDto getClReportById(Integer checklistId){

        EquipmentsGoogleChecklist equipmentsChecklist = equipmentsGoogleChecklistDao.findById(checklistId)
                .orElseThrow(() -> new IllegalArgumentException("Checklist report not found"));

        List<GoogleChecklist> googleChecklists = googleChecklistDao
                .findByEquipmentsGoogleChecklistsIdAndStatus(checklistId, "1");

        EquipmentsGoogleChecklistCreateDto checklistDto =
                clToDto(equipmentsChecklist);

        List<GoogleCheckListCreateDto> listDto =
                googleChecklists.stream()
                        .map(this::checklistsToDto)
                        .toList();

        checklistDto.setChecklists(listDto);

        return checklistDto;
    }

    private GoogleCheckListCreateDto checklistsToDto(GoogleChecklist checklist){
        return GoogleCheckListCreateDto
                .builder()
                .googleChecklistsId(checklist.getGoogleChecklistsId())
                .equipmentNumber(checklist.getEquipmentNumber())
                .equipmentName(checklist.getEquipmentName())
                .operator(checklist.getOperator())
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
                .otherType(checklist.getOtherType())
                .build();
    }

    private EquipmentsGoogleChecklistCreateDto clToDto(EquipmentsGoogleChecklist equipmentsChecklist){
        return EquipmentsGoogleChecklistCreateDto
                .builder()
                .equipmentsGoogleChecklistsId(equipmentsChecklist.getEquipmentsGoogleChecklistsId())
                .jobsId(equipmentsChecklist.getJobsId())
                .userName(equipmentsChecklist.getUpdatedBy())
                .date(equipmentsChecklist.getDate())
                .build();
    }

}
