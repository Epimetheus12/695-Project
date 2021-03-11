package com.worker.people.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "users")
public class User {
    @Id
    String id;
    String userName;
    String email;
    String gender;
    String nickName;
    String password;
    String summary;
    String maritalStatus;
    String birthday;
    String[] follower;
    String[] followed;
    String firstName;
    String lastName;
    Map<String, String> location;
    String[] hobby;

    public User() {

    }

    public User(String id, String userName, String email, String gender, String nickName, String password, String summary, String maritalStatus, String birthday, String[] follower, String[] followed, String firstName, String lastName, Map<String, String> location, String[] hobby) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.nickName = nickName;
        this.password = password;
        this.summary = summary;
        this.maritalStatus = maritalStatus;
        this.birthday = birthday;
        this.follower = follower;
        this.followed = followed;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.hobby = hobby;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public String getSummary() {
        return summary;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getBirthday() {
        return birthday;
    }

    public String[] getFollower() {
        return follower;
    }

    public String[] getFollowed() {
        return followed;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Map<String, String> getLocation() {
        return location;
    }

    public String[] getHobby() {
        return hobby;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public void setLocation(Map<String, String> location) {
        this.location = location;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }
}