package com.worker.people.validations;


import com.worker.people.domain.entities.User;
import com.worker.people.domain.models.RegisterModel;
import com.worker.people.domain.models.UserServiceModel;
import com.worker.people.domain.models.UserUpdateModel;


import org.springframework.security.core.userdetails.UserDetails;

public interface UserValidation {
    boolean isValid(User user);

    boolean isValid(UserServiceModel userServiceModel);

    boolean isValid(RegisterModel userRegisterBindingModel);

    boolean isValid(String firstParam, String secondParam);

    boolean isValid(UserUpdateModel userUpdateBindingModel);

    boolean isValid(UserDetails userData);
}
