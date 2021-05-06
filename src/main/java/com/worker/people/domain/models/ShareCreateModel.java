package com.worker.people.domain.models;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class ShareCreateModel {

    private String timelineUserId;
    private String loggedInUserId;
    private String content;
    private ArrayList<MultipartFile> datas;

    public ShareCreateModel() {
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
    public String getLoggedInUserId() {
        return this.loggedInUserId;
    }

    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    @NotNull
    @NotEmpty
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<MultipartFile> getDatas( ) {
        return datas;
    }

    public void setDatas(ArrayList<MultipartFile> datas) {
        this.datas = datas;
    }
}
