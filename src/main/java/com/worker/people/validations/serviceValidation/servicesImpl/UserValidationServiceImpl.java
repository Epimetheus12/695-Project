package com.worker.people.validations.serviceValidation.servicesImpl;

import com.worker.people.domain.entities.User;
import com.worker.people.domain.models.UserUpdateModel;
import com.worker.people.validations.serviceValidation.services.UserValidationService;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {

    @Override
    public boolean isValid(UserUpdateModel userUpdateModel){
        return userUpdateModel != null;
    }

    @Override
    public boolean isValid(User user){return user!=null;}
}
