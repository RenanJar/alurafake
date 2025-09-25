package br.com.alura.AluraFake.api.controller;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("task/")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("new")
    public ResponseEntity createNewTask(@RequestBody @Valid TaskDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",taskService.createNewTask(request)));
    }

}