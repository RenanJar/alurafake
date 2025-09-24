package br.com.alura.AluraFake.repository;

import br.com.alura.AluraFake.domain.taskAnswer.TaskAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAnswerRepository extends JpaRepository<TaskAnswer, Integer>{

}
