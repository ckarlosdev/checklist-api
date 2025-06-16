package com.ck.wi.controller.googleChecklist;

import com.ck.wi.service.googleChecklist.IEquipmentsGoogleChecklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173"
})
@RestController
@RequestMapping("/api/v1")
public class EquipmentsGoogleChecklistController {
    @Autowired
    private IEquipmentsGoogleChecklist equipmentsGoogleChecklistService;

}
