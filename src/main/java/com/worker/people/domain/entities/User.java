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

    public String getId( ) {
        return id;
    }

    public String getUserName( ) {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
