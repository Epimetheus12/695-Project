package com.worker.people.servicesImpl;

import com.worker.people.domain.entities.User;
import com.worker.people.domain.models.RegisterModel;
import com.worker.people.domain.models.UserServiceModel;
import com.worker.people.domain.models.UserUpdateModel;
import com.worker.people.validations.UserValidation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserValidationImpl implements UserValidation {

    @Override
    public boolean isValid(User user) {
        return user != null;
    }

    @Override
    public boolean isValid(UserServiceModel userServiceModel) {
        return userServiceModel != null;
    }

    @Override
    public boolean isValid(RegisterModel userRegisterBindingModel) {
        return userRegisterBindingModel != null && isValid(userRegisterBindingModel.getPassword(), userRegisterBindingModel.getConfirmPassword());
    }

    @Override
    public boolean isValid(String firstParam, String secondParam) {
        return firstParam.equals(secondParam);
    }

    @Override
    public boolean isValid(UserUpdateModel userUpdateBindingModel) {
        return userUpdateBindingModel != null;
    }

    @Override
    public boolean isValid(UserDetails userData) {
        return userData != null;
    }
}
