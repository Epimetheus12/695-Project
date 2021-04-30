package com.worker.people.services;

import com.worker.people.domain.models.ShareCreateModel;
import com.worker.people.domain.models.ShareServiceModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ShareService {
    boolean createPost(ShareCreateModel postCreateBindingModel) throws Exception;

    List<ShareServiceModel> getAllPosts(String timelineUserId);

    CompletableFuture<Boolean> deletePost(String loggedInUserId, String postToRemoveId) throws Exception;
}
