package br.com.alura.AluraFake.util;

import br.com.alura.AluraFake.task.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskTypeAllowed {
    Type[] value();
}
