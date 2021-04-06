package com.worker.people.domain.models;

import com.worker.people.validations.Password;
import com.worker.people.utils.ValidationMessage;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;

public class UserLoginModel implements Serializable {

    private String username;
    private String password;

    public UserLoginModel() {

    }

    @Pattern(regexp = "^([a-zA-Z0-9]+)$")
    @Size(min = 4, max = 16, message = ValidationMessage.INVALID_CREDENTIALS_MESSAGE)
    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Password(minLength = 8, maxLength = 30, passwordPattern = true, message = ValidationMessage.INVALID_CREDENTIALS_MESSAGE )
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
