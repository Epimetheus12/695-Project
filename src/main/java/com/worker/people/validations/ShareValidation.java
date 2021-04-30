package com.worker.people.validations;

import com.worker.people.domain.entities.Share;
import com.worker.people.domain.models.ShareCreateModel;
import org.springframework.stereotype.Component;

@Component
public interface ShareValidation {
    boolean isValid(Share share);

    boolean isValid(ShareCreateModel ShareCreateModel);
}
