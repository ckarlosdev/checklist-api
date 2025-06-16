package com.ck.wi.service.impl.hazard;

import com.ck.wi.model.dao.hazard.PreTaskDao;
import com.ck.wi.model.dto.hazard.ActivityDto;
import com.ck.wi.model.dto.hazard.PreTaskDto;
import com.ck.wi.model.dto.hazard.PretasksOptionDto;
import com.ck.wi.model.entity.hazard.PreTask;
import com.ck.wi.service.hazard.IPreTask;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PreTaskImpl implements IPreTask {

    @Autowired
    private PreTaskDao preTaskDao;

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
}
