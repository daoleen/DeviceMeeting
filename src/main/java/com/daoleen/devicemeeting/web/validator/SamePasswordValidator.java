package com.daoleen.devicemeeting.web.validator;

import com.daoleen.devicemeeting.web.validator.annotation.SamePassword;
import com.daoleen.devicemeeting.web.viewmodel.Passwords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.*;
import java.util.Set;

/**
 * Created by alex on 18.6.14.
 */
public class SamePasswordValidator implements ConstraintValidator<SamePassword, Passwords> {
    private final static Logger logger = LoggerFactory.getLogger(SamePasswordValidator.class);

    @Autowired
    private Validator validator;

    @Override
    public void initialize(SamePassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(@Valid Passwords value, ConstraintValidatorContext context) {
        Set<ConstraintViolation<Passwords>> constraintViolations = validator.validateProperty(value, "password");
        constraintViolations.forEach(
                c -> {
                    logger.debug("Constraint Message Template is: {}", c.getMessageTemplate());
                    logger.debug("Constraint Massage is: {}", c.getMessage());
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(c.getMessageTemplate()).addConstraintViolation();
                });

        return (value.getPassword().equals(value.getPasswordAgain()) && constraintViolations.isEmpty());
    }
}