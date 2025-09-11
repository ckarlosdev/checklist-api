package com.ck.wi.service.demoChecklist;

import com.ck.wi.model.dto.demoChecklist.DemoChecklistCreateDto;
import com.ck.wi.model.entity.demoChecklist.DemoChecklist;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IDemoChecklist {
    DemoChecklist processAndSaveDemoChecklist(DemoChecklistCreateDto demoChecklistCreateDto);

    Optional<DemoChecklistCreateDto> getDemoChecklistByID(Integer id);

    List<DemoChecklist> getDemoChecklistByjobNumber(String jobNumber);
}
