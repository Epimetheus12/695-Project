package com.worker.people.domain.models;

import com.worker.people.domain.entities.User;

import java.time.LocalDateTime;

public class CommentServiceModel {
    private String id;
    private String ShareId;
    private String authorId;
    private User timelineUser;
    private String content;
    private LocalDateTime time;
    private String imageUrl;

    public CommentServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId( ) {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getShareId( ) {
        return ShareId;
    }

    public void setShareId(String shareId) {
        ShareId = shareId;
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

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
