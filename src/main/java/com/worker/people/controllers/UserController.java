package com.worker.people.controllers;


import com.worker.people.domain.entities.User;
import com.worker.people.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public Iterable<User> user() {
        return userRepository.findAll();
    }
}
