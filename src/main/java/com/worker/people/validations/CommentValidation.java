package com.worker.people.validations;

import com.worker.people.domain.entities.Comment;
import com.worker.people.domain.models.CommentCreateModel;
import org.springframework.stereotype.Component;

@Component
public interface CommentValidation {
    boolean isValid(Comment comment);

    boolean isValid(CommentCreateModel commentCreateModel);
}
