package com.worker.people.domain.models;

import com.worker.people.utils.ValidationMessage;
import com.worker.people.validations.Password;
import com.worker.people.validations.UniqueEmail;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserUpdateModel implements Serializable {

    private String id;
    private String username;
    private String email;
    private String nickname;
    private String password;
    private String summary;
    private String maritalStatus;
    private String[] follower;
    private String[] followed;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String[] hobby;
    private String picURl;
    private String backgroundImageUrl;
    private String gender;
    private String birthday;

    public UserUpdateModel( ) {
    }

    @NotNull(message = ValidationMessage.ID_REQUIRED_MESSAGE)
    @Length(min = 1, message = ValidationMessage.ID_REQUIRED_MESSAGE)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Pattern(regexp = "^([a-zA-Z0-9]+)$")
    @Size(min = 4, max = 16, message = ValidationMessage.USER_INVALID_USERNAME_MESSAGE)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = ValidationMessage.USER_INVALID_EMAIL_MESSAGE)
    public String getEmail( ) {
        return email;
    }

    @Pattern(regexp = "(male|female)", message = "Invalid gender")
    public String getGender( ) {
        return gender;
    }

    @DateTimeFormat
    public String getBirthday( ) {
        return birthday;
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

    public String getSummary( ) {
        return summary;
    }

    @Pattern(regexp = "(single|married)", message = "Invalid marital status")
    public String getMaritalStatus( ) {
        return maritalStatus;
    }

    public String[] getFollower( ) {
        return follower;
    }

    public String[] getFollowed( ) {
        return followed;
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

    public String[] getHobby( ) {
        return hobby;
    }

    public String getPicURl( ) {
        return picURl;
    }

    public String getBackgroundImageUrl(){return backgroundImageUrl;}


    public void setEmail(String email) {
        this.email = email;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setFollower(String[] follower) {
        this.follower = follower;
    }

    public void setFollowed(String[] followed) {
        this.followed = followed;
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

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }

    public void setPicURl(String picURl) {
        this.picURl = picURl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
