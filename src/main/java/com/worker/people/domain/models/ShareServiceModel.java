package com.worker.people.domain.models;

import com.worker.people.domain.entities.User;

import java.time.LocalDateTime;

public class ShareServiceModel {

    private String id;
    private User loggedInUser;
    private User timelineUser;
    private String content;
    private String imageUrl;
    private LocalDateTime time;
    private int likeNum;
    private String[] commentId;


    public ShareServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getTimelineUser() {
        return this.timelineUser;
    }

    public void setTimelineUser(User timelineUser) {
        this.timelineUser = timelineUser;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


}
