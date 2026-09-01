package com.ck.wi.controller.issue;

import com.ck.wi.model.dto.issue.IssueReportRequestDto;
import com.ck.wi.model.dto.issue.IssueReportResponseDto;
import com.ck.wi.service.issue.IIssueReport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/issue-reports")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
public class IssueReportController {

    private final IIssueReport issueReportService;

    // 1. Obtener un reporte por su ID
    @GetMapping("/{id}")
    public ResponseEntity<IssueReportResponseDto> getById(@PathVariable Long id) {
        IssueReportResponseDto response = issueReportService.findById(id);
        return ResponseEntity.ok(response);
    }

    // 2. Obtener la lista de reportes asociados a una máquina (Equipment)
    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<List<IssueReportResponseDto>> getByEquipment(@PathVariable Integer equipmentId) {
        List<IssueReportResponseDto> response = issueReportService.findByEquipment(equipmentId);
        return ResponseEntity.ok(response);
    }

    // 3. Crear un nuevo reporte
    @PostMapping
    public ResponseEntity<IssueReportResponseDto> create(@RequestBody IssueReportRequestDto requestDto) {
        IssueReportResponseDto response = issueReportService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 4. Eliminar un reporte (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        issueReportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
