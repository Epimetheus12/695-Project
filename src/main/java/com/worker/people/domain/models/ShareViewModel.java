package com.worker.people.domain.models;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShareViewModel {
    private String id;
    private String loggedInUserProfilePicUrl;
    private String author;
    private String authorId;
    private String timelineUserId;
    private String content;
    private ArrayList<String> imageUrls;
    private List<String> likeNum;
    private LocalDateTime time;
    private List<CommentViewModel> commentViewList;

    public ShareViewModel() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId( ) {
        return id;
    }

    public String getLoggedInUserProfilePicUrl() {
        return this.loggedInUserProfilePicUrl;
    }

    public void setLoggedInUserProfilePicUrl(String loggedInUserProfilePicUrl) {
        this.loggedInUserProfilePicUrl = loggedInUserProfilePicUrl;
    }

    public String getAuthor( ) {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorId( ) {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getImageUrls( ) {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getLikeNum( ) {
        return likeNum;
    }

    public void setLikeNum(List<String> likeNum) {
        this.likeNum = likeNum;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getTimelineUserId() {
        return this.timelineUserId;
    }

    public void setTimelineUserId(String timelineUserId) {
        this.timelineUserId = timelineUserId;
    }

    public List<CommentViewModel> getCommentViewList( ) {
        return commentViewList;
    }

    public void setCommentViewList(List<CommentViewModel> commentViewList) {
        this.commentViewList = commentViewList;
    }
}
