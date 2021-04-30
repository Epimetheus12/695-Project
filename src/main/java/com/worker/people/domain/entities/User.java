package com.worker.people.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;
    private String username;
    private String email;
    private String gender;
    private String nickname;
    private String password;
    private String summary;
    private String maritalStatus;
    private String birthday;
    private String[] follower;
    private String[] followed;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String[] hobby;
    private String profilePicURL;
    private String backgroundPicURL;
    private Set<Role> authorities;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;


    public User( ) {

    }

    public User(String id, String username, String email, String gender, String nickname, String password, String summary, String maritalStatus, String birthday, String[] follower, String[] followed, String firstName, String lastName, String address, String city, String[] hobby, String profilePicURL, String backgroundPicURL, Set<Role> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.nickname = nickname;
        this.password = password;
        this.summary = summary;
        this.maritalStatus = maritalStatus;
        this.birthday = birthday;
        this.follower = follower;
        this.followed = followed;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.hobby = hobby;
        this.profilePicURL = profilePicURL;
        this.backgroundPicURL = backgroundPicURL;
        this.authorities = authorities;


    }

    public String getId( ) {
        return id;
    }

    public String getUsername( ) {
        return username;
    }

    @Override
    public boolean isAccountNonExpired( ) {
        return true;
    }

    @Override
    public boolean isAccountNonLocked( ) {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired( ) {
        return true;
    }

    @Override
    public boolean isEnabled( ) {
        return true;
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

    public String[] getFollower( ) {
        return follower;
    }

    public String[] getFollowed( ) {
        return followed;
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

    public String getProfilePicURL( ) {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public Set<Role> getAuthorities( ) {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public String getBackgroundPicURL( ) {
        return backgroundPicURL;
    }

    public void setBackgroundPicURL(String backgroundPicURL) {
        this.backgroundPicURL = backgroundPicURL;
    }


    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}