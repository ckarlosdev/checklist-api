package com.ck.wi.service;

import com.ck.wi.model.dto.ChecklistDto;
import com.ck.wi.model.dto.JobDto;
import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.googleChecklist.EquipmentsGoogleChecklist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IChecklist {

    Checklist processAndSaveChecklist(ChecklistDto checklistDto);

    List<Checklist> findByJob(Job job);

    Checklist findById(Integer id);

    List<Checklist> findAll();

    void delete(Checklist checklist);

    List<Checklist> findByJobAndDate(Integer jobsId, LocalDate date);

}
