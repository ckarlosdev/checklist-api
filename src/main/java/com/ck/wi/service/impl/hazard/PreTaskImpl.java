package com.ck.wi.service.impl.hazard;

import com.ck.wi.model.dao.hazard.*;
import com.ck.wi.model.dto.hazard.ActivityDto;
import com.ck.wi.model.dto.hazard.PreTaskDto;
import com.ck.wi.model.dto.hazard.PretasksOptionDto;
import com.ck.wi.model.dto.hazard.create.*;
import com.ck.wi.model.entity.hazard.*;
import com.ck.wi.service.hazard.IPreTask;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PreTaskImpl implements IPreTask {

    @Autowired
    private PreTaskDao preTaskDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private PretasksOptionDao pretasksOptionDao;

    @Autowired
    private PretasksCheckboxOptionDao pretasksCheckboxOptionDao;

    @Autowired
    private PtSignaruteDao ptSignaruteDao;

    private static final String ACTIVE = "1";
    private static final String INACTIVE = "0";

    @Override
    public PreTask findById(Integer id){
        return preTaskDao.findById(id).orElse(null);
    }

    @Override
    public PreTask findByJobsIdAndDate(Integer jobId, LocalDate date){
        return preTaskDao.findByJobsIdAndDate(jobId, date);
    }

    @Transactional
    public Optional<PreTaskDto> getPretaskWithActivities(Integer id){
        Optional<PreTask> pretaskWithActivities = preTaskDao.findByIdWithActivities(id);

        if (!pretaskWithActivities.isPresent()) {
            return Optional.empty();
        }

        PreTask pretask = pretaskWithActivities.get();
        Optional<PreTask> pretaskWithOption = preTaskDao.findByIdWithOptions(id);

        pretaskWithOption.ifPresent(p -> {
            p.getPretasksOption().size();
            pretask.setPretasksOption(p.getPretasksOption());
        });


        List<ActivityDto> activityDtos = pretask.getActivities().stream()
                .map(activity ->
                        ActivityDto.builder()
                                .activitiesId(activity.getActivitiesId())
                                .activity(activity.getActivity())
                                .hazards(activity.getHazards())
                                .controls(activity.getControls())
                                .build())
                .collect(Collectors.toList());

        List<PretasksOptionDto> pretasksOptionDtos = pretask.getPretasksOption().stream()
                .map(pretasksOption ->
                        PretasksOptionDto.builder()
                                .pretasksOptionsId(pretasksOption.getPretasksOptionsId())
                                .pretasksCheckboxOptionsId(pretasksOption.getPretasksCheckboxOptionsId())
                                .optionName(pretasksOption.getCheckboxOption() != null ? pretasksOption.getCheckboxOption().getName() : null)
                                .optionType(pretasksOption.getCheckboxOption() != null ? pretasksOption.getCheckboxOption().getType() : null)
                                .other(pretasksOption.getOther())
                                .build())
                .collect(Collectors.toList());

        return Optional.of(new PreTaskDto(
                pretask.getPreTasksId(),
                pretask.getJobsId(),
                pretask.getDate(),
                pretask.getSupervisor(),
                pretask.getComment(),
                activityDtos,
                pretasksOptionDtos // Asegúrate de que PreTaskDto acepte esta lista
        ));
    }

    @Transactional
    @Override
    public Integer savePretask(PreTaskCreateDto preTaskCreateDto){

        LocalDateTime today = LocalDateTime.now();

        System.out.println("DTO Completo: " + preTaskCreateDto.toString());
        PreTask preTask = toEntity(preTaskCreateDto, today);
        PreTask preTaskSaved = preTaskDao.save(preTask);

        // save activities
        if(!preTaskCreateDto.getActivities().isEmpty()){
            saveActivities(
                preTaskCreateDto.getActivities(),
                today,
                preTaskSaved
            );
        }

        // save options
        if(!preTaskCreateDto.getOptions().isEmpty()){
            saveOptions(
                preTaskCreateDto.getOptions(),
                today,
                preTaskSaved
            );
        }

        // save sinatures
        if(!preTaskCreateDto.getSignatures().isEmpty()){
            saveSignatures(
                preTaskCreateDto.getSignatures(),
                today,
                preTaskSaved
            );
        }

        return preTaskSaved.getPreTasksId();
    }

    private void saveSignatures(
            List<PtSignatureDto> signatures,
            LocalDateTime today,
            PreTask preTask
    ){
        for(PtSignatureDto dto : signatures){
            PtSignature signature = PtSignature.builder()
                    .preTasksId(preTask.getPreTasksId())
                    .employeesId(dto.getEmployeesId())
                    .imgData(dto.getImgData())
                    .createdBy(preTask.getCreatedBy())
                    .createdDate(today)
                    .updatedBy(preTask.getCreatedBy())
                    .updatedDate(today)
                    .ptSignaturesStatus(ACTIVE)
                    .build();
            ptSignaruteDao.save(signature);
        }
    }

    private void saveOptions(
            List<PreTaskOptionCreateDto> options,
            LocalDateTime today,
            PreTask preTask
    ){
        for(PreTaskOptionCreateDto dto : options){
            PretasksOption pretasksOption = PretasksOption.builder()
                    .preTask(preTask)
                    .pretasksCheckboxOptionsId(dto.getPretasksCheckboxOptionsId())
                    .other(dto.getOther())
                    .createdBy(preTask.getCreatedBy())
                    .createdDate(today)
                    .updatedBy(preTask.getCreatedBy())
                    .updatedDate(today)
                    .status(ACTIVE)
                    .build();
            pretasksOptionDao.save(pretasksOption);
        }
    }

    private void saveActivities(
            List<ActivityCreateDto> activities,
            LocalDateTime today,
            PreTask preTask
    ){
        for(ActivityCreateDto dto : activities){
            Activity activity = Activity.builder()
                    .preTask(preTask)
                    .activity(dto.getActivity())
                    .hazards(dto.getHazards())
                    .controls(dto.getControls())
                    .createdBy(preTask.getCreatedBy())
                    .createdDate(today)
                    .updatedBy(preTask.getCreatedBy())
                    .updatedDate(today)
                    .status(ACTIVE)
                    .build();
            activityDao.save(activity);
        }
    }

    private PreTask toEntity(
            PreTaskCreateDto preTaskCreateDto,
            LocalDateTime today
    ){
         return PreTask.builder()
                .jobsId(preTaskCreateDto.getJobsId())
                .date(preTaskCreateDto.getDate())
                .supervisor(preTaskCreateDto.getSupervisor())
                .comment(preTaskCreateDto.getComment())
                .createdBy(preTaskCreateDto.getUserName())
                .createdDate(today)
                .updatedBy(preTaskCreateDto.getUserName())
                .updatedDate(today)
                .status(ACTIVE)
                .token("")
                .build();
    }

    @Transactional
    @Override
    public void updatePretask(PreTaskCreateDto preTaskCreateDto){
        PreTask preTaskToUpdate = preTaskDao.findById(preTaskCreateDto.getPreTasksId())
                .orElseThrow(() -> new IllegalArgumentException("Hazard report not found."));

        LocalDateTime now = LocalDateTime.now();

        preTaskToUpdate.setJobsId(preTaskCreateDto.getJobsId());
        preTaskToUpdate.setDate(preTaskCreateDto.getDate());
        preTaskToUpdate.setSupervisor(preTaskCreateDto.getSupervisor());
        preTaskToUpdate.setComment(preTaskCreateDto.getComment());
        preTaskToUpdate.setUpdatedBy(preTaskCreateDto.getUserName());
        preTaskToUpdate.setUpdatedDate(now);

        PreTask preTaskUpdated = preTaskDao.save(preTaskToUpdate);

        udpateActivities(preTaskCreateDto.getActivities(), now, preTaskUpdated);
        updateOptions(preTaskCreateDto.getOptions(), now, preTaskUpdated);
        updateSignatures(preTaskCreateDto.getSignatures(), now, preTaskUpdated);

    }

    private void updateSignatures(List<PtSignatureDto> signatures, LocalDateTime now, PreTask preTask){
        String updater = preTask.getUpdatedBy();

        List<PtSignature> actualSignatures = ptSignaruteDao.findByPreTasksIdAndPtSignaturesStatus(preTask.getPreTasksId(), "1");
        Map<Integer, PtSignature> actualSignaturesMap =
                actualSignatures.stream()
                        .collect(Collectors.toMap(PtSignature::getPtSignaturesId, Function.identity()));
        List<PtSignature> signaturesToSave = new ArrayList<>();

        for(PtSignatureDto dto : signatures){
            Integer dtoId = dto.getPtSignaturesId();

            if(dtoId != null && actualSignaturesMap.containsKey(dtoId)){
                PtSignature signature = actualSignaturesMap.get(dtoId);

                signature.setEmployeesId(dto.getEmployeesId());
                signature.setImgData(dto.getImgData());
                signature.setUpdatedBy(updater);
                signature.setUpdatedDate(now);

                signaturesToSave.add(signature);
                actualSignaturesMap.remove(dtoId);
            }else{
                PtSignature newSignature = PtSignature.builder()
                    .preTasksId(preTask.getPreTasksId())
                    .employeesId(dto.getEmployeesId())
                    .imgData(dto.getImgData())
                    .createdBy(updater)
                    .createdDate(now)
                    .updatedBy(updater)
                    .updatedDate(now)
                    .ptSignaturesStatus(ACTIVE)
                    .build();

                signaturesToSave.add(newSignature);
            }
        }

        ptSignaruteDao.saveAll(signaturesToSave);
        List<PtSignature> signatureToDelete = new ArrayList<>(actualSignaturesMap.values());

        if(!signatureToDelete.isEmpty()){
            signatureToDelete.forEach(sign -> {
                sign.setPtSignaturesStatus(INACTIVE);
                sign.setUpdatedBy(updater);
                sign.setUpdatedDate(now);
            });
            ptSignaruteDao.saveAll(signatureToDelete);
        }
    }

    private void updateOptions(List<PreTaskOptionCreateDto> options, LocalDateTime now, PreTask preTask){
        String updater = preTask.getUpdatedBy();
        System.out.println("updations actual for  ***********************************************************************");
        List<PretasksOption> actualOptions = pretasksOptionDao.findByPreTaskIdAndStatus(preTask.getPreTasksId(), "1");
        System.out.println("updations actual for updates" + actualOptions.toString());
        Map<Integer, PretasksOption> actualOptionsMap =
                actualOptions.stream()
                        .collect(Collectors.toMap(PretasksOption::getPretasksOptionsId, Function.identity()));
        List<PretasksOption> optionsToSave = new ArrayList<>();

        for(PreTaskOptionCreateDto dto : options){
            Integer dtoId = dto.getPretasksOptionsId();
//            PretasksCheckboxOption checkboxOption =
//                    pretasksCheckboxOptionDao.findById(dto.getPretasksCheckboxOptionsId()).orElse(null);

            if(dtoId != null && actualOptionsMap.containsKey(dtoId)){
                PretasksOption option = actualOptionsMap.get(dtoId);

                option.setPretasksCheckboxOptionsId(dto.getPretasksCheckboxOptionsId());
                option.setOther(dto.getOther());
                option.setUpdatedBy(updater);
                option.setUpdatedDate(now);

                optionsToSave.add(option);
                actualOptionsMap.remove(dtoId);
            }else{
                PretasksOption newOption = PretasksOption.builder()
                        .preTask(preTask)
                        .pretasksCheckboxOptionsId(dto.getPretasksCheckboxOptionsId())
                        .other(dto.getOther())
                        .createdBy(updater)
                        .createdDate(now)
                        .updatedBy(updater)
                        .updatedDate(now)
                        .status(ACTIVE)
                        .build();

                optionsToSave.add(newOption);
            }
        }

        pretasksOptionDao.saveAll(optionsToSave);
        List<PretasksOption> optionsToDelete = new ArrayList<>(actualOptionsMap.values());

        if(!optionsToDelete.isEmpty()){
            optionsToDelete.forEach(opt -> {
                opt.setStatus(INACTIVE);
                opt.setUpdatedBy(updater);
                opt.setUpdatedDate(now);
            });
            pretasksOptionDao.saveAll(optionsToDelete);
        }
    }

    private void udpateActivities(List<ActivityCreateDto> activities, LocalDateTime now, PreTask preTask){
        String updater = preTask.getUpdatedBy();

        List<Activity> actualActivities = activityDao.findByPreTaskIdAndStatus(preTask.getPreTasksId(), "1");
        Map<Integer, Activity> actualActivitiesMap =
                actualActivities.stream()
                        .collect(Collectors.toMap(Activity::getActivitiesId, Function.identity()));
        List<Activity> activitiesToSave = new ArrayList<>();

        for(ActivityCreateDto dto : activities){
            Integer dtoId = dto.getActivitiesId();
            if(dtoId != null && actualActivitiesMap.containsKey(dtoId)){
                Activity activity = actualActivitiesMap.get(dtoId);

                activity.setActivity(dto.getActivity());
                activity.setHazards(dto.getHazards());
                activity.setControls(dto.getControls());
                activity.setUpdatedBy(updater);
                activity.setUpdatedDate(now);

                activitiesToSave.add(activity);
                actualActivitiesMap.remove(dtoId);
            }else{
                Activity newActivity = Activity.builder()
                        .preTask(preTask)
                        .activity(dto.getActivity())
                        .hazards(dto.getHazards())
                        .controls(dto.getControls())
                        .createdBy(updater)
                        .createdDate(now)
                        .updatedBy(updater)
                        .updatedDate(now)
                        .status(ACTIVE)
                        .build();
                activitiesToSave.add(newActivity);
            }
        }

        activityDao.saveAll(activitiesToSave);
        List<Activity> activitiesToDelete = new ArrayList<>(actualActivitiesMap.values());

        if(!activitiesToDelete.isEmpty()){
            activitiesToDelete.forEach(act -> {
                act.setStatus(INACTIVE);
                act.setUpdatedBy(updater);
                act.setUpdatedDate(now);
            });
            activityDao.saveAll(activitiesToDelete);
        }
    }

    @Transactional
    @Override
    public PreTaskCreateDto getReportById(Integer pretaskId){
        PreTask preTask = preTaskDao.findById(pretaskId)
                .orElseThrow(() -> new IllegalArgumentException("report not found"));;

        List<ActivityCreateDto> activityDtosList =
                activityDao.findByPreTaskIdAndStatus(pretaskId, "1")
                        .stream()
                        .map(this::toActivityDto)
                        .toList();

        List<PreTaskOptionCreateDto> optionsDtosList =
                pretasksOptionDao.findByPreTaskIdAndStatus(pretaskId, "1")
                        .stream()
                        .map(this::toOptionDto)
                        .toList();

        List<PtSignatureDto> signaturesDtosList =
                ptSignaruteDao.findByPreTasksIdAndPtSignaturesStatus(pretaskId, "1")
                        .stream()
                        .map(this::toSignatureDto)
                        .toList();

        PreTaskCreateDto preTaskCreateDto = pretaskToDto(preTask);
        preTaskCreateDto.setActivities(activityDtosList);
        preTaskCreateDto.setOptions(optionsDtosList);
        preTaskCreateDto.setSignatures(signaturesDtosList);

        return preTaskCreateDto;
    }

    private PtSignatureDto toSignatureDto(PtSignature signature){
        return PtSignatureDto.builder()
                .ptSignaturesId(signature.getPtSignaturesId())
                .employeesId(signature.getEmployeesId())
                .imgData(signature.getImgData())
                .build();
    }

    private PreTaskOptionCreateDto toOptionDto(PretasksOption option){
        return PreTaskOptionCreateDto.builder()
                .pretasksOptionsId(option.getPretasksOptionsId())
                .pretasksCheckboxOptionsId(option.getPretasksCheckboxOptionsId())
                .other(option.getOther())
                .build();
    }

    private ActivityCreateDto toActivityDto(Activity activity){
        return ActivityCreateDto.builder()
                .activitiesId(activity.getActivitiesId())
                .activity(activity.getActivity())
                .hazards(activity.getHazards())
                .controls(activity.getControls())
                .build();
    }

    private PreTaskCreateDto pretaskToDto(PreTask pretask){
        return PreTaskCreateDto.builder()
                .preTasksId(pretask.getPreTasksId())
                .jobsId(pretask.getJobsId())
                .userName(pretask.getUpdatedBy())
                .date(pretask.getDate())
                .supervisor(pretask.getSupervisor())
                .comment(pretask.getComment())
                .build();
    }

    @Transactional
    @Override
    public List<PretaskViewDto> getPretasksByJobId(Integer jobId){
        return preTaskDao.findPretaskById(jobId);
    }



















}
