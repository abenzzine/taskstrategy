package com.taskstrategy.commons.domain.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by brian on 1/31/14.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidTaskDateValidator.class)
@Documented
public @interface ValidTaskDate {
    String message() default "{com.taskstrategy.constraints.validtaskdate}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}