package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.api.dto.task.TaskAnswerDTO;
import br.com.alura.AluraFake.domain.task.entity.TaskAnswer;
import br.com.alura.AluraFake.domain.task.util.TaskAnswerMapper;
import br.com.alura.AluraFake.infra.repository.TaskAnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskAnswerServiceTest {

    private TaskAnswerRepository taskAnswerRepository;
    private TaskAnswerMapper taskAnswerMapper;
    private TaskAnswerService taskAnswerService;

    @BeforeEach
    void setUp() {
        taskAnswerRepository = mock(TaskAnswerRepository.class);
        taskAnswerMapper = mock(TaskAnswerMapper.class);
        taskAnswerService = new TaskAnswerService(taskAnswerRepository, taskAnswerMapper);
    }

    @Test
    void shouldSaveAllTaskAnswersAndReturnSavedEntities() {
        TaskAnswerDTO dto1 = new TaskAnswerDTO();
        TaskAnswerDTO dto2 = new TaskAnswerDTO();
        List<TaskAnswerDTO> dtos = List.of(dto1, dto2);

        TaskAnswer entity1 = new TaskAnswer();
        TaskAnswer entity2 = new TaskAnswer();

        when(taskAnswerMapper.toEntity(dto1, 10)).thenReturn(entity1);
        when(taskAnswerMapper.toEntity(dto2, 10)).thenReturn(entity2);

        when(taskAnswerRepository.save(entity1)).thenReturn(entity1);
        when(taskAnswerRepository.save(entity2)).thenReturn(entity2);


        List<TaskAnswer> savedEntities = taskAnswerService.saveList(dtos, 10);

        assertEquals(2, savedEntities.size());
        assertTrue(savedEntities.contains(entity1));
        assertTrue(savedEntities.contains(entity2));

        verify(taskAnswerMapper).toEntity(dto1, 10);
        verify(taskAnswerMapper).toEntity(dto2, 10);
        verify(taskAnswerRepository).save(entity1);
        verify(taskAnswerRepository).save(entity2);
    }

    @Test
    void shouldThrowExceptionIfMapperFails() {
        TaskAnswerDTO dto = new TaskAnswerDTO();
        List<TaskAnswerDTO> dtos = List.of(dto);

        when(taskAnswerMapper.toEntity(dto, 5)).thenThrow(new RuntimeException("Mapper error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                taskAnswerService.saveList(dtos, 5)
        );

        assertEquals("Mapper error", ex.getMessage());
        verify(taskAnswerMapper).toEntity(dto, 5);
        verify(taskAnswerRepository, never()).save(any());
    }
}