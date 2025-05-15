package com.ck.wi.service;

import com.ck.wi.model.dto.ChecklistDto;
import com.ck.wi.model.dto.JobDto;
import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Job;

import java.util.List;

public interface IChecklist {
    Checklist processAndSaveChecklist(ChecklistDto checklistDto);

    Checklist findByJob(Job job);

    Checklist findById(Integer id);

    List<Checklist> findAll();

    void delete(Checklist checklist);

}
