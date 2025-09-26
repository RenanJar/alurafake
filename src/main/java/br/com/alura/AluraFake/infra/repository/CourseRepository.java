package br.com.alura.AluraFake.infra.repository;

import br.com.alura.AluraFake.domain.enumeration.Status;
import br.com.alura.AluraFake.domain.course.entity.Course;
import br.com.alura.AluraFake.infra.repository.projections.InstructorCourseReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    boolean existsCourseByIdAndStatus(Long courseId, Status status);

    @Modifying
    @Transactional
    @Query("""
            UPDATE Course course
            SET course.status = :status,
                course.publishedAt = :now
            WHERE course.id = :courseId
            """)
    int publish(@Param("courseId") Long courseId,
                @Param("status") Status status,
                @Param("now") LocalDateTime now);

    @Query(""" 
            select
             course.id AS id,
             course.title AS title,
             course.status AS status ,
             course.publishedAt AS publishedAt,
             COUNT(task.id) AS taskCount
            FROM Course course
            LEFT JOIN course.tasks task
            WHERE course.instructor.id = :instructorId
            GROUP BY course.id, course.title, course.status, course.publishedAt
            """)
    List<InstructorCourseReportProjection> exportInstructorCourseReportData(@Param("instructorId") Long instructorId);

    @Query("""
        SELECT COUNT(course)
        FROM Course course
        WHERE course.instructor.id = :instructorId AND course.status = 'PUBLISHED'
    """)
    Integer countInstructorCourseReportData(@Param("instructorId") Long instructorId);
}
