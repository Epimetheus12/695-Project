package com.worker.people.validations.serviceValidation.services;

import org.springframework.security.core.userdetails.UserDetails;
import com.worker.people.domain.models.UserUpdateModel;

public interface UserValidationService {
    Boolean isValid(UserUpdateModel userUpdateModel);
}
