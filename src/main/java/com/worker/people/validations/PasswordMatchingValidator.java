package com.worker.people.validations;

import com.worker.people.domain.models.RegisterModel;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, Object> {
    @Override
    public void initialize(PasswordMatching constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o instanceof RegisterModel) {
            RegisterModel user = (RegisterModel) o;
            return user.getPassword().equals(user.getConfirmPassword());
        }
        return false;
    }
}
