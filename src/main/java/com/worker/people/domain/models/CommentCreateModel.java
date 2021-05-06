package com.worker.people.domain.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CommentCreateModel {
    private String shareId;
    private String loggedInUserId;
    private String timelineUserId;
    private String content;
    private String imageUrl;

    @NotNull
    @NotEmpty
    public String getShareId() {
        return this.shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    @NotNull
    @NotEmpty
    public String getLoggedInUserId() {
        return this.loggedInUserId;
    }

    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    @NotNull
    @NotEmpty
    public String getTimelineUserId() {
        return this.timelineUserId;
    }

    public void setTimelineUserId(String timelineUserId) {
        this.timelineUserId = timelineUserId;
    }

    @NotNull
    @NotEmpty
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

}
