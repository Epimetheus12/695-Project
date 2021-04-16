package com.worker.people.validations.serviceValidation.servicesImpl;

import com.worker.people.domain.models.UserUpdateModel;
import com.worker.people.validations.serviceValidation.services.UserValidationService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {

    @Override
    public Boolean isValid(UserUpdateModel userUpdateModel){
        return userUpdateModel != null;
    }
}
