package br.com.alura.AluraFake.infra.repository;

import br.com.alura.AluraFake.domain.task.entity.TaskAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAnswerRepository extends JpaRepository<TaskAnswer, Integer>{

}
