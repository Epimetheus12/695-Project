package com.worker.people.validations;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {
    private int minLength;
    private int maxLength;
    private boolean passwordPattern;

    @Override
    public void initialize(Password password){
        this.maxLength = password.maxLength();
        this.minLength = password.minLength();
        this.passwordPattern = password.passwordPattern();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.,!@#$%^&+=])(?=\\S+$).{8,30}$";

        if (password.length() < this.minLength || password.length() > this.maxLength){
            System.out.println("password postion 1");
            return false;
        }

        if(!Pattern.matches(regex, password) && this.passwordPattern){

            System.out.println("password postion 2" + password + Pattern.matches(regex, password));
            return false;
        }

        /*System.out.println("password postion 3");*/
        return true;
    }

}
