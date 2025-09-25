package br.com.alura.AluraFake.infra.repository;

import br.com.alura.AluraFake.domain.enumeration.Status;
import br.com.alura.AluraFake.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
}
