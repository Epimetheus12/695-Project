package com.worker.people.services;

import com.worker.people.domain.models.CommentViewModel;
import com.worker.people.domain.models.ShareCreateModel;
import com.worker.people.domain.models.ShareServiceModel;
import com.worker.people.domain.models.ShareViewModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ShareService {
    boolean createPost(String timelineUserId, String loggedInUserId, String content, ArrayList<MultipartFile> datas) throws Exception;

    List<ShareViewModel> getAllShares(String timelineUserId);

    CompletableFuture<Boolean> deletePost(String loggedInUserId, String postToRemoveId) throws Exception;
    /*List<CommentViewModel> getComments(String shareId) throws Exception;*/
}
