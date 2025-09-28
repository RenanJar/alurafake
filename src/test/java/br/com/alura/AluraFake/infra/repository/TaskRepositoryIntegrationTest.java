package br.com.alura.AluraFake.infra.repository;

import br.com.alura.AluraFake.domain.course.entity.Course;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.task.entity.Task;
import br.com.alura.AluraFake.domain.user.entity.User;
import br.com.alura.AluraFake.integrationtest.base.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TaskRepositoryIntegrationTest extends IntegrationTestBase {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private User instructor;
    private Course course;

    @BeforeEach
    void setUp() {
        instructor = userRepository.findById(2L).get();
        course = courseRepository.findById(1L).get();
    }

    @Test
    void shouldFindTasksByCourseId() {
        Task task1 = new Task();
        task1.setStatement("Task 1");
        task1.setOrder(1);
        task1.setTypeTask(Type.SINGLE_CHOICE);
        task1.setCourse(course);

        Task task2 = new Task();
        task2.setStatement("Task 2");
        task2.setOrder(2);
        task2.setTypeTask(Type.SINGLE_CHOICE);
        task2.setCourse(course);

        taskRepository.saveAll(List.of(task1, task2));

        List<Task> tasks = taskRepository.findByCourseId(course.getId());
        assertThat(tasks).hasSize(2);
    }

    @Test
    void shouldUpdateOrder() {
        Task task1 = new Task();
        task1.setStatement("Task 1");
        task1.setOrder(1);
        task1.setTypeTask(Type.SINGLE_CHOICE);
        task1.setCourse(course);

        Task task2 = new Task();
        task2.setStatement("Task 2");
        task2.setOrder(2);
        task2.setTypeTask(Type.SINGLE_CHOICE);
        task2.setCourse(course);

        taskRepository.saveAll(List.of(task1, task2));
        clearAndFlushEntityManager();

        int updated = taskRepository.updateOrder(course.getId(), 1);
        taskRepository.flush();
        assertThat(updated).isEqualTo(2);

        List<Task> tasks = taskRepository.findByCourseIdOrderByOrderAsc(course.getId());
        assertThat(tasks.get(0).getOrder()).isEqualTo(2);
        assertThat(tasks.get(1).getOrder()).isEqualTo(3);
    }

    @Test
    void shouldCheckIfHasAllTaskTypes() {
        Task singleChoiceTask = new Task();
        singleChoiceTask.setStatement("Task 1");
        singleChoiceTask.setOrder(1);
        singleChoiceTask.setTypeTask(Type.SINGLE_CHOICE);
        singleChoiceTask.setCourse(course);

        Task multipleChoiceTask = new Task();
        multipleChoiceTask.setStatement("Task 2");
        multipleChoiceTask.setOrder(2);
        multipleChoiceTask.setTypeTask(Type.MULTIPLE_CHOICE);
        multipleChoiceTask.setCourse(course);

        Task openTextTask = new Task();
        openTextTask.setStatement("Task 3");
        openTextTask.setOrder(3);
        openTextTask.setTypeTask(Type.OPEN_TEXT);
        openTextTask.setCourse(course);

        taskRepository.saveAll(List.of(singleChoiceTask, multipleChoiceTask, openTextTask));

        boolean hasAll = taskRepository.hasAllTaskTypes(course.getId(), Type.values().length);
        assertThat(hasAll).isTrue();
    }

    @Test
    void shouldCheckIfTaskOrderIsContinuous() {
        Task task1 = new Task();
        task1.setStatement("Task 1");
        task1.setOrder(1);
        task1.setTypeTask(Type.SINGLE_CHOICE);
        task1.setCourse(course);

        Task task2 = new Task();
        task2.setStatement("Task 2");
        task2.setOrder(2);
        task2.setTypeTask(Type.SINGLE_CHOICE);
        task2.setCourse(course);

        taskRepository.saveAll(List.of(task1, task2));

        Boolean continuous = taskRepository.isTaskOrderContinuous(course.getId());
        assertThat(continuous).isTrue();


        task2.setOrder(5);
        taskRepository.save(task2);

        Boolean continuousAfterChange = taskRepository.isTaskOrderContinuous(course.getId());
        assertThat(continuousAfterChange).isFalse();
    }

    @Test
    void shouldFindTasksByCourseIdOrdered() {
        Task task1 = new Task();
        task1.setStatement("Task 1");
        task1.setOrder(2);
        task1.setTypeTask(Type.SINGLE_CHOICE);
        task1.setCourse(course);

        Task task2 = new Task();
        task2.setStatement("Task 2");
        task2.setOrder(1);
        task2.setTypeTask(Type.SINGLE_CHOICE);
        task2.setCourse(course);

        taskRepository.saveAll(List.of(task1, task2));

        List<Task> tasks = taskRepository.findByCourseIdOrderByOrderAsc(course.getId());
        assertThat(tasks.get(0).getOrder()).isEqualTo(1);
        assertThat(tasks.get(1).getOrder()).isEqualTo(2);
    }
}