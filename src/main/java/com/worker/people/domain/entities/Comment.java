package com.worker.people.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private Share share;
    private User creator;
    private User timelineUser;
    private String content;
    private LocalDateTime time;
    private String imageUrl;

    public Comment(){

    }

    public String getId( ) {
        return id;
    }

    public Share getShare( ) {
        return share;
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

    public void setShare(Share share) {
        this.share = share;
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
}

