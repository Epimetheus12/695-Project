package com.worker.people.services;

import com.worker.people.domain.models.CommentCreateModel;

import java.util.concurrent.CompletableFuture;

public interface CommentService {
    boolean createComment(CommentCreateModel commentCreateBindingModel) throws Exception;

    CompletableFuture<Boolean> deleteComment(String loggedInUserId, String commentToRemoveId) throws Exception;
}
