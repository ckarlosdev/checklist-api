package com.ck.wi.service.googleChecklist;

import com.ck.wi.model.entity.googleChecklist.GoogleChecklist;

import java.time.LocalDate;
import java.util.List;

public interface IGoogleChecklist {
    List<GoogleChecklist> findByEquipmentsGoogleChecklistsId(Integer id);
}
