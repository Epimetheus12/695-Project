package com.worker.people.domain.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "shares")
public class Share {
    @Id
    private String id;
    private String author;
    private String authorId;
    private String content;
    private List<String> likeNum;
    private LocalDateTime time;
    private List<String> commentList;
    private ArrayList<String> imageUrls;

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

    public List<String> getLikeNum( ) {
        return likeNum;
    }

    public void setLikeNum(List<String> likeNum) {
        this.likeNum = likeNum;
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



    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<String> getCommentList( ) {
        return commentList;
    }

    public void setCommentList(List<String> commentList) {
        this.commentList = commentList;
    }

    public ArrayList<String> getImageUrls( ) {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
