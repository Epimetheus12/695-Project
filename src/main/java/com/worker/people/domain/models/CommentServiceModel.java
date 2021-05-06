package com.worker.people.domain.models;

import com.worker.people.domain.entities.Share;
import com.worker.people.domain.entities.User;

import java.time.LocalDateTime;

public class CommentServiceModel {
    private String id;
    private String shareId;
    private User creator;
    private User timelineUser;
    private String content;
    private LocalDateTime time;
    private String imageUrl;

    public CommentServiceModel() {
    }

    public String getId( ) {
        return id;
    }

    public User getCreator( ) {
        return creator;
    }

    public User getTimelineUser( ) {
        return timelineUser;
    }

    public String getContent( ) {
        return content;
    }

    public LocalDateTime getTime( ) {
        return time;
    }

    public String getImageUrl( ) {
        return imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setTimelineUser(User timelineUser) {
        this.timelineUser = timelineUser;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShareId( ) {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }
}
