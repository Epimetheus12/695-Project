package com.worker.people.validations.serviceValidation.servicesImpl;

import com.worker.people.domain.entities.Post;
import com.worker.people.domain.models.PostCreateModel;
import com.worker.people.validations.serviceValidation.services.PostValidationService;
import org.springframework.stereotype.Component;

@Component
public class PostValidationServiceImpl implements PostValidationService {
    @Override
    public boolean isValid(Post post) {
        return post != null;
    }

    @Override
    public boolean isValid(PostCreateModel postCreateModel) {
        return postCreateModel != null;
    }
}
