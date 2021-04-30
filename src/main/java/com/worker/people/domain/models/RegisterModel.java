package com.worker.people.domain.models;

import com.worker.people.utils.ValidationMessage;
import com.worker.people.validations.Password;
import com.worker.people.validations.UniqueEmail;
import com.worker.people.validations.UniqueUsername;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class RegisterModel implements Serializable {

    private String username;
    private String email;
    private String gender;
    private String nickname;
    private String password;
    private String confirmPassword;
    private String maritalStatus;
    private String birthday;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String profilePicURL;
    private String backgroundPicURL;

    public RegisterModel( ) {
    }

    @Pattern(regexp = "^([a-zA-Z0-9]+)$", message = ValidationMessage.USER_INVALID_USERNAME_MESSAGE)
    @Size(min = 4, max = 16, message = ValidationMessage.USER_INVALID_USERNAME_MESSAGE)
    @UniqueUsername
    public String getUsername( ) {
        return username;
    }

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = ValidationMessage.USER_INVALID_EMAIL_MESSAGE)
    @UniqueEmail
    public String getEmail( ) {
        return email;
    }

    @Pattern(regexp = "(male|female)", message = "Invalid gender")
    public String getGender( ) {
        return gender;
    }

    @Pattern(regexp = "^([a-zA-Z0-9]+)$")
    @Size(min = 4, max = 16, message = "Invalid nickname")
    public String getNickname( ) {
        return nickname;
    }

    @Password(minLength = 8, maxLength = 30, passwordPattern = true, message = ValidationMessage.INVALID_CREDENTIALS_MESSAGE)
    public String getPassword( ) {
        return password;
    }

    public String getConfirmPassword( ) {
        return confirmPassword;
    }

    @Pattern(regexp = "(single|married)", message = "Invalid marital status")
    public String getMaritalStatus( ) {
        return maritalStatus;
    }

    @DateTimeFormat
    public String getBirthday( ) {
        return birthday;
    }

    @Pattern(regexp = "^[A-Z]([a-zA-Z]+)?$", message = ValidationMessage.USER_INVALID_FIRST_NAME_MESSAGE)
    public String getFirstName( ) {
        return firstName;
    }

    @Pattern(regexp = "^[A-Z]([a-zA-Z]+)?$", message = ValidationMessage.USER_INVALID_LAST_NAME_MESSAGE)
    public String getLastName( ) {
        return lastName;
    }

    @NotNull(message = ValidationMessage.USER_ADDRESS_REQUIRED_MESSAGE)
    @Length(min = 1, message =  ValidationMessage.USER_ADDRESS_REQUIRED_MESSAGE)
    public String getAddress( ) {
        return address;
    }

    @NotNull(message = ValidationMessage.USER_CITY_REQUIRED_MESSAGE)
    @Length(min = 1, message = ValidationMessage.USER_CITY_REQUIRED_MESSAGE)
    public String getCity( ) {
        return city;
    }

    public String getProfilePicURL( ) {
        return profilePicURL;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public String getBackgroundPicURL( ) {
        return backgroundPicURL;
    }

    public void setBackgroundPicURL(String backgroundPicURL) {
        this.backgroundPicURL = backgroundPicURL;
    }
}
