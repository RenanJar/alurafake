package br.com.alura.AluraFake.integrationtest.domain.course;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.api.dto.report.InstructorCourseReportDTO;
import br.com.alura.AluraFake.domain.enumeration.Status;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.integrationtest.base.IntegrationTestBase;
import br.com.alura.AluraFake.integrationtest.helper.TaskHelper;
import br.com.alura.AluraFake.service.CourseService;
import br.com.alura.AluraFake.service.ReportService;
import br.com.alura.AluraFake.service.TaskService;
import br.com.alura.AluraFake.util.report.ReportResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.com.google.common.collect.MoreCollectors.onlyElement;

public class ExportCourseInstructorReportIT extends IntegrationTestBase {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ReportService reportService;

    private TaskDTO taskSingleChoice = new TaskDTO();
    private TaskDTO taskMultiChoice = new TaskDTO();
    private TaskDTO taskOpenText = new TaskDTO();
    private ReportResponse<InstructorCourseReportDTO> report;
    private InstructorCourseReportDTO result;


    @Test
    @DisplayName("report Must Contain All Registered Data Report")
    public void reportMustContainAllRegisteredDataReport() {
        givenAvalidTaskSingleChoice();
        givenAvalidTaskMultipleChoice();
        givenAvalidTaskOpenText();
        andCreateTasksToCourse();
        andPublishCurse();
        whenExtractInstructorReport();

        thenMustContainApublishedCourse();
        andTotalOfTaskInThisCourseIs(3);
        andTotalPublishedCourseByThisInstructorIs(1);
    }

    private void andTotalPublishedCourseByThisInstructorIs(Integer totalExpected) {
        assertEquals(totalExpected, report.getTotalPublished());
    }

    private void andTotalOfTaskInThisCourseIs(Integer totalExpected) {
        assertEquals(totalExpected, result.getTaskCount());
    }

    private void thenMustContainApublishedCourse() {
        result = report.getContent().stream().filter(report -> report.getStatus().equals(Status.PUBLISHED)).collect(onlyElement());
    }

    private void whenExtractInstructorReport() {
        report = reportService.generateInstructorCourseReport(2L);
    }

    private void andPublishCurse() {
        courseService.publishCourse(1L);
    }

    private void andCreateTasksToCourse() {
        taskService.createNewTask(taskSingleChoice);
        taskService.createNewTask(taskMultiChoice);
        taskService.createNewTask(taskOpenText);
    }

    void givenAvalidTaskSingleChoice() {
        taskSingleChoice.setCourseId(1);
        taskSingleChoice.setOrder(1);
        taskSingleChoice.setStatement("Pergunta Teste");
        taskSingleChoice.setType(Type.SINGLE_CHOICE);
        taskSingleChoice.setOptions(TaskHelper.getSingleCoiceTaskAswer());
    }

    void givenAvalidTaskMultipleChoice() {
        taskMultiChoice.setCourseId(1);
        taskMultiChoice.setOrder(1);
        taskMultiChoice.setStatement("Pergunta Teste 2");
        taskMultiChoice.setType(Type.MULTIPLE_CHOICE);
        taskMultiChoice.setOptions(TaskHelper.getMultipleCoiceTaskAswer());
    }

    void givenAvalidTaskOpenText() {
        taskOpenText.setCourseId(1);
        taskOpenText.setOrder(1);
        taskOpenText.setStatement("Pergunta Teste 3");
        taskOpenText.setType(Type.OPEN_TEXT);
    }

}
