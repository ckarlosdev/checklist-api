package com.ck.wi.controller.dasbhoard;

import com.ck.wi.model.dto.dashboard.CalendarEventDTO;
import com.ck.wi.service.assigment.IAssignment;
import com.ck.wi.service.impl.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    @Autowired
    private IAssignment assignmentService;

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/events")
    public ResponseEntity<List<CalendarEventDTO>> getCalendarEvents(
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate
    ){
        List<CalendarEventDTO> events = dashboardService.getEvents(startDate, endDate);

        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(events);
    }
    // http://localhost:8080/assigned?start=2026-04-01&end=2026-04-30


    @GetMapping("/events/{jobId}")
    public ResponseEntity<List<CalendarEventDTO>> getCalendarEventsById(
            @PathVariable Integer jobId
    ){
        List<CalendarEventDTO> events = dashboardService.getEventsById(jobId);

        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(events);
    }


}
