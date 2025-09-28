package br.com.alura.AluraFake.integrationtest.repository;

import br.com.alura.AluraFake.domain.course.entity.Course;
import br.com.alura.AluraFake.domain.enumeration.Status;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.task.entity.Task;
import br.com.alura.AluraFake.domain.task.entity.TaskAnswer;
import br.com.alura.AluraFake.domain.user.entity.User;
import br.com.alura.AluraFake.infra.repository.projections.InstructorCourseReportProjection;
import br.com.alura.AluraFake.integrationtest.base.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseRepositoryIntegrationTest extends IntegrationTestBase {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private User instructor = new User();

    @BeforeEach
    void setUp() {
        instructor = userRepository.findById(2L).get();
    }

    @Test
    void shouldCheckIfCourseExistsByIdAndStatus() {
        Course course = new Course("Java Basics", "learn Java Essentials", instructor);
        courseRepository.save(course);

        boolean exists = courseRepository.existsCourseByIdAndStatus(course.getId(), Status.BUILDING);

        assertThat(exists).isTrue();
    }

    @Test
    void shouldPublishCourse() {
        Course course = new Course("Java Basics", "learn Java Essentials", instructor);
        courseRepository.save(course);

        clearAndFlushEntityManager();
        int updated = courseRepository.publish(course.getId(), Status.PUBLISHED, LocalDateTime.now());

        assertThat(updated).isEqualTo(1);

        Course publishedCourse = courseRepository.findById(course.getId()).orElseThrow();
        assertThat(publishedCourse.getStatus()).isEqualTo(Status.PUBLISHED);
        assertThat(publishedCourse.getPublishedAt()).isNotNull();
    }

    @Test
    void shouldExportInstructorCourseReportData() {
        Course courseNotExportable = new Course("Java Basics 2", "learn Java Essentials 2", instructor);
        courseRepository.save(courseNotExportable);

        Course courseExportable = new Course("Java Basics", "learn Java Essentials", instructor);

        TaskAnswer taskAnswer = new TaskAnswer();
        taskAnswer.setCorrect(true);
        taskAnswer.setOptionText("Option 1");

        Task task = new Task();
        task.setCourse(courseExportable);
        task.setTypeTask(Type.SINGLE_CHOICE);
        task.setStatement("Test");
        task.setOrder(1);

        task.addAnswer(taskAnswer);

        courseExportable.addTask(task);

        courseRepository.save(courseExportable);
        courseRepository.publish(courseExportable.getId(), Status.PUBLISHED, LocalDateTime.now());

        List<InstructorCourseReportProjection> result = courseRepository.exportInstructorCourseReportData(2L);

        assertEquals(3, result.size());

        InstructorCourseReportProjection projection = result.stream()
                .filter(item -> item.getTitle().equals(courseExportable.getTitle()))
                .findFirst()
                .get();

        assertThat(projection.getTaskCount()).isEqualTo(1);
        assertThat(projection.getStatus()).isEqualTo(Status.PUBLISHED);
    }

    @Test
    void shouldCountPublishedCoursesByInstructor() {
        Course course = new Course("Java Basics", "learn Java Essentials", instructor);
        Course course2 = new Course("Java Basics 2", "learn Java Essentials 2", instructor);

        courseRepository.save(course);
        courseRepository.save(course2);

        courseRepository.publish(course2.getId(), Status.PUBLISHED, LocalDateTime.now());

        Integer count = courseRepository.countInstructorCourseReportData(2L);

        assertThat(count).isEqualTo(1);
    }
}