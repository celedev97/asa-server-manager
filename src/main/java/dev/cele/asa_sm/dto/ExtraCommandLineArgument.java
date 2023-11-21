package dev.cele.asa_sm.dto;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ExtraCommandLineArgument {
    String value();
    boolean invertBoolean() default false;
    //add a numeric condition for checking if the value is 0 or not
}
