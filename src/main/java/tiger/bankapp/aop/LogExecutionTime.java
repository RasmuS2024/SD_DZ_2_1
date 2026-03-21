package tiger.bankapp.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для замера времени выполнения метода
 */
@Target(ElementType.METHOD) //аннотация для методов
@Retention(RetentionPolicy.RUNTIME) //во время выполнения доступно
public @interface LogExecutionTime {
    String name() default "LogExecutionTime";
}
