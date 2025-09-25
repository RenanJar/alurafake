package br.com.alura.AluraFake.domain.task.rules;

import br.com.alura.AluraFake.domain.enumeration.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskTypeAllowed {
    Type[] value();
}
