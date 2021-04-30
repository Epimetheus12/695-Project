package com.worker.people.domain.models;

public class FriendsCandidatesViewModel {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String profilePicURL;
    private String backgroundPicURL;
    private Boolean starterOfAction = false;

    public FriendsCandidatesViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicURL() {
        return this.profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public String getBackgroundPicURL() {
        return this.backgroundPicURL;
    }

    public void setBackgroundPicURL(String backgroundPicURL) {
        this.backgroundPicURL = backgroundPicURL;
    }

    public Boolean getStarterOfAction() {
        return this.starterOfAction;
    }

    public void setStarterOfAction(Boolean starterOfAction) {
        this.starterOfAction = starterOfAction;
    }

//    public Integer getStatus() {
//        return this.status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
}
