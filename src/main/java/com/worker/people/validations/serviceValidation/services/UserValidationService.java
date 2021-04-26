package com.worker.people.validations.serviceValidation.services;

import com.worker.people.domain.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.worker.people.domain.models.UserUpdateModel;

public interface UserValidationService {
    boolean isValid(UserUpdateModel userUpdateModel);

    boolean isValid(User user);
}
