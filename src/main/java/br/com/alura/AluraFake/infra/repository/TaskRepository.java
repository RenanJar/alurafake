package br.com.alura.AluraFake.infra.repository;

import br.com.alura.AluraFake.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer>{

    List<Task> findByCourseId(Integer courseId);

    @Modifying
    @Query("""
            UPDATE Task task
            SET task.order = task.order + 1
            WHERE task.courseId = :cursoId
            AND task.order >= :ordem
            """)
    int updateOrder(@Param("cursoId") Integer cursoId,
                    @Param("ordem") Integer ordem);

    @Query("""
       SELECT CASE WHEN COUNT(DISTINCT t.typeTask) = :totalTypes THEN true ELSE false END
       FROM Task t
       WHERE t.course.id = :courseId
    """)
    boolean hasAllTaskTypes(@Param("courseId") Long courseId, @Param("totalTypes") long totalTypes);

    @Query("""
            SELECT (MAX(t.order) - MIN(t.order) + 1) = COUNT(t)
            FROM Task t
            WHERE t.course.id = :courseId
            """)
    Boolean isTaskOrderContinuous(@Param("courseId") Long courseId);

    List<Task>findByCourseIdOrderByOrderAsc(Integer courseId);
}
