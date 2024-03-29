package com.worker.people.servicesImpl;

import com.worker.people.domain.entities.Comment;
import com.worker.people.domain.models.CommentCreateModel;
import com.worker.people.validations.CommentValidation;
import org.springframework.stereotype.Component;

@Component
public class CommentValidationImpl implements CommentValidation {
    @Override
    public boolean isValid(Comment comment) {
        return comment != null;
    }

    @Override
    public boolean isValid(CommentCreateModel commentCreateModel) {
        return commentCreateModel != null;
    }
}
