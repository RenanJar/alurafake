package br.com.alura.AluraFake.api.controller;

import br.com.alura.AluraFake.api.dto.report.InstructorCourseReportDTO;
import br.com.alura.AluraFake.service.ReportService;
import br.com.alura.AluraFake.util.report.ReportResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
public class InstructorController {


    private final ReportService reportService;


    public InstructorController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<ReportResponse<InstructorCourseReportDTO>> reportCrouses(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.generateInstructorCourseReport(id));
    }

}
