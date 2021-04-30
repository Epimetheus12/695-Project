package com.worker.people.validations;

import com.worker.people.domain.entities.Picture;
import org.springframework.stereotype.Component;

@Component
public interface PictureValidation {
    boolean isValid(Picture picture);
}
