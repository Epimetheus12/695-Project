package com.worker.people.domain.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "shares")
public class Share {
    @Id
    private String id;
    private String author;
    private String authorId;
    private String content;
    private String shareId;
    private int likeNum;
    private LocalDateTime time;
    private String[] commentId;
//    private String[] imageID;

    public Share() {

    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public String getShareId() {
        return shareId;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
