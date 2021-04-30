package com.worker.people.servicesImpl;

import com.worker.people.domain.entities.Picture;
import com.worker.people.validations.PictureValidation;
import org.springframework.stereotype.Component;


public class PictureValidationImpl implements PictureValidation {
    @Override
    public boolean isValid(Picture picture) {
        return picture != null;
    }
}
