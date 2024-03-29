package com.worker.people.domain.models;

import com.worker.people.domain.entities.Role;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserServiceModel implements Serializable {
    private String id;
    private String username;
    private String email;
    private String gender;
    private String nickname;
    private String password;
    private String summary;
    private String maritalStatus;
    private String birthday;
    private List<String> follower;
    private List<String> followed;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String[] hobby;
    private String profilePicURL;
    private String backgroundPicURL;
    private Set<Role> authorities;

    public UserServiceModel() {
        this.authorities = new HashSet<>();
    }

    public String getId( ) {
        return id;
    }

    public String getUsername( ) {
        return username;
    }

    public String getEmail( ) {
        return email;
    }

    public String getGender( ) {
        return gender;
    }

    public String getNickname( ) {
        return nickname;
    }

    public String getPassword( ) {
        return password;
    }

    public String getSummary( ) {
        return summary;
    }

    public String getMaritalStatus( ) {
        return maritalStatus;
    }

    public String getBirthday( ) {
        return birthday;
    }

    public List<String> getFollowed( ) {
        return followed;
    }

    public List<String> getFollower( ) {
        return follower;
    }

    public String getFirstName( ) {
        return firstName;
    }

    public String getLastName( ) {
        return lastName;
    }

    public String getAddress( ) {
        return address;
    }

    public String getCity( ) {
        return city;
    }

    public String[] getHobby( ) {
        return hobby;
    }

    public String getProfilePicURL( ) {
        return profilePicURL;
    }

    public Set<Role> getAuthorities( ) {
        return authorities;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setFollowed(List<String> followed) {
        this.followed = followed;
    }

    public void setFollower(List<String> follower) {
        this.follower = follower;
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

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public String extractAuthority( ) {
        return this.getAuthorities().stream().findFirst().orElse(null).getAuthority();
    }

    public String getBackgroundPicURL( ) {
        return backgroundPicURL;
    }

    public void setBackgroundPicURL(String backgroundPicURL) {
        this.backgroundPicURL = backgroundPicURL;
    }
}
