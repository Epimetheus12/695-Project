package com.worker.people.validations.serviceValidation.services;

import com.worker.people.domain.entities.Post;
import com.worker.people.domain.models.PostCreateModel;

public interface PostValidationService {
    boolean isValid(Post post);

    boolean isValid(PostCreateModel postCreateModel);
}
