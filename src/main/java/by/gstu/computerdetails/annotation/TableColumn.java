package by.gstu.computerdetails.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TableColumn {
    String name() default "UnnamedColumn";

    int index();

    boolean hidden() default false;
}
