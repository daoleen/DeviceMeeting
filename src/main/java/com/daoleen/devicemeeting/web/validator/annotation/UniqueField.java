package com.daoleen.devicemeeting.web.validator.annotation;

import com.daoleen.devicemeeting.web.validator.UniqueFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by alex on 18.6.14.
 */

@Constraint(validatedBy = UniqueFieldValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {
    String message() default "{validation.uniquefield}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String tableName() default "users";

    String fieldName() default "email";
}