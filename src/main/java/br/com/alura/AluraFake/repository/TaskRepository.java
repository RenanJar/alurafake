package br.com.alura.AluraFake.repository;

import br.com.alura.AluraFake.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer>{

    List<Task> findByCourseId(Integer courseId);

}
