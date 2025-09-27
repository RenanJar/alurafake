package br.com.alura.AluraFake.api.controller;

import br.com.alura.AluraFake.api.dto.report.InstructorCourseReportDTO;
import br.com.alura.AluraFake.domain.error.exception.EntityNotFoundException;
import br.com.alura.AluraFake.domain.error.exception.InvalidUserRoleException;
import br.com.alura.AluraFake.service.ReportService;
import br.com.alura.AluraFake.util.report.ReportResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InstructorController.class)
class InstructorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    void shouldReturnOkWhenInstructorIsValid() throws Exception {
        InstructorCourseReportDTO dto = new InstructorCourseReportDTO();
        ReportResponse<InstructorCourseReportDTO> response =
                new ReportResponse<>(List.of(dto), 1);

        when(reportService.generateInstructorCourseReport(1L))
                .thenReturn(response);

        mockMvc.perform(get("/instructor/{id}/courses", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenInstructorNotFound() throws Exception {
        when(reportService.generateInstructorCourseReport(anyLong()))
                .thenThrow(new EntityNotFoundException("Instructor not found"));

        mockMvc.perform(get("/instructor/{id}/courses", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Resource not found"))
                .andExpect(jsonPath("$.detail").value("Instructor not found"))
                .andExpect(jsonPath("$.type", containsString("/resource-not-found")));
    }

    @Test
    void shouldReturn400WhenInstructorRoleIsInvalid() throws Exception {
        when(reportService.generateInstructorCourseReport(anyLong()))
                .thenThrow(new InvalidUserRoleException("Insufficient role privileges."));

        mockMvc.perform(get("/instructor/{id}/courses", 2L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid instructor role"))
                .andExpect(jsonPath("$.detail").value("Insufficient role privileges."))
                .andExpect(jsonPath("$.type", containsString("/invalid-instructor-role")));
    }
}