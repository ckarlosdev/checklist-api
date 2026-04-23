package com.ck.wi.controller.hazard;

import com.ck.wi.model.entity.hazard.PretasksCheckboxOption;
import com.ck.wi.service.hazard.IPretasksCheckboxOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class PretasksCheckboxOptionController {

    @Autowired
    private IPretasksCheckboxOption pretasksCheckboxOptionService;

    @GetMapping("/ptCheckboxOptions")
    public List<PretasksCheckboxOption> getCheckBoxOptions(){
        return pretasksCheckboxOptionService.getAll();
    }

}
