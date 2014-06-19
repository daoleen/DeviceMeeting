package com.daoleen.devicemeeting.web.validator.annotation;

import com.daoleen.devicemeeting.web.validator.SamePasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by alex on 18.6.14.
 */

@Constraint(validatedBy = SamePasswordValidator.class)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SamePassword {
    String message() default "{validation.account.password.same}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}