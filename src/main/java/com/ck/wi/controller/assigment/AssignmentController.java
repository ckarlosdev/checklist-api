package com.ck.wi.controller.assigment;

import com.ck.wi.model.dto.assignment.AssignmentCreateDto;
import com.ck.wi.model.dto.assignment.AssignmentDto;
import com.ck.wi.model.entity.assignment.Assignment;
import com.ck.wi.service.assigment.IAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class AssignmentController {

    @Autowired
    private IAssignment assignmentService;

    @PostMapping("assignment")
    public AssignmentDto saveAssignment(@RequestBody AssignmentCreateDto assignmentCreateDto){
        return assignmentService.save(assignmentCreateDto);
    }

    @PutMapping("assignment")
    public AssignmentDto updateAssignmet(@RequestBody AssignmentCreateDto assignmentCreateDto){
        return assignmentService.save(assignmentCreateDto);
    }

    @GetMapping("assignment/{assigmentsId}")
    public AssignmentDto getAssigmentById(@PathVariable Integer assigmentsId){
        return assignmentService.getById(assigmentsId);
    }

    @GetMapping("assignments")
    public List<Assignment> getAllAssigmnets(){
        return assignmentService.getAssignments();
    }
}
