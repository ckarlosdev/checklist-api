package com.ck.wi.service;

import com.ck.wi.model.dto.ChecklistDto;
import com.ck.wi.model.entity.Checklist;

public interface IChecklist {
    Checklist processAndSaveChecklist(ChecklistDto checklistDto);

    Checklist findById(Integer id);

    void delete(Checklist checklist);

}
