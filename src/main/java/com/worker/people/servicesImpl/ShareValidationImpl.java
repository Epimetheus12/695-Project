package com.worker.people.servicesImpl;

import com.worker.people.domain.entities.Share;
import com.worker.people.domain.models.ShareCreateModel;
import com.worker.people.validations.ShareValidation;
import org.springframework.stereotype.Component;

@Component
public class ShareValidationImpl implements ShareValidation {
    @Override
    public boolean isValid(Share post) {
        return post != null;
    }

    @Override
    public boolean isValid(ShareCreateModel postCreateBindingModel) {
        return postCreateBindingModel != null;
    }

}
