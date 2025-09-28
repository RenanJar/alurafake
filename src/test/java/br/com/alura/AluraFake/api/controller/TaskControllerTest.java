package br.com.alura.AluraFake.api.controller;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.error.exception.EntityNotFoundException;
import br.com.alura.AluraFake.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    private TaskDTO request = new TaskDTO();

    @BeforeEach
    void setUp() {
        request.setType(Type.OPEN_TEXT);
        request.setStatement("Statement");
        request.setOrder(1);
        request.setCourseId(123L);
    }

    @Test
    void shouldCreateTaskSuccessfully() throws Exception {


        when(taskService.createNewTask(any(TaskDTO.class))).thenReturn(123L);

        mockMvc.perform(post("/task/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(123)));
    }

    @Test
    void shouldReturn404WhenEntityNotFound() throws Exception {
        when(taskService.createNewTask(any(TaskDTO.class)))
                .thenThrow(new EntityNotFoundException("Course not found"));

        mockMvc.perform(post("/task/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Resource not found"))
                .andExpect(jsonPath("$.detail").value("Course not found"))
                .andExpect(jsonPath("$.type", containsString("/resource-not-found")));
    }

    @Test
    void shouldReturn400WhenValidationFails() throws Exception {

        TaskDTO invalidRequest = new TaskDTO();

        mockMvc.perform(post("/task/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation error"))
                .andExpect(jsonPath("$.type", containsString("/validation-error")))
                .andExpect(jsonPath("$.errors").isArray());
    }
}