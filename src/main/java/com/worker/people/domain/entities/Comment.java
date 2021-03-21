package com.worker.people.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String author;
    private String authorId;
    private String content;
    private String shareId;
    private int like_num;
    private String time;

    public Comment(){

    }
    public Comment(String id, String author, String authorId, String content, String shareId, int like_num, String time){
        this.id = id;
        this.author = author;
        this.authorId = authorId;
        this.content = content;
        this.shareId = shareId;
        this.like_num = like_num;
        this.time = time;
    }
    public String getId(){
        return id;
    }
    public String getAuthor(){
        return author;
    }
    public String getAuthorId(){
        return authorId;
    }
    public String getContent(){
        return content;
    }
    public String getShareId(){
        return shareId;
    }
    public int getLike_num(){
        return like_num;
    }
    public String getTime(){
        return time;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setAuthorId(String authorId){
        this.authorId = authorId;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setShareId(String shareId){
        this.shareId = shareId;
    }
    public void setLike_num(int like_num){
        this.like_num = like_num;
    }
    public void setTime(String time){
        this.time = time;
    }

}

