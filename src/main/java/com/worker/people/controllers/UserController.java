package com.worker.people.controllers;


import com.worker.people.domain.entities.User;
import com.worker.people.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/create")
    public User save(@RequestBody User user){
        userRepository.save(user);

        return user;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseGet(User::new);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateTutorial(@PathVariable("id") String id, @RequestBody User user){
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()){
            User _user = userData.get();
            _user.setUserName(user.getUserName());
            _user.setEmail(user.getEmail());
            _user.setGender(user.getGender());
            _user.setNickName(user.getNickName());
            _user.setPassword(user.getPassword());
            _user.setSummary(user.getSummary());
            _user.setMaritalStatus(user.getMaritalStatus());
            _user.setBirthday(user.getBirthday());
            _user.setFirstName(user.getFirstName());
            _user.setLastName(user.getLastName());
            _user.setLocation(user.getLocation());
            _user.setHobby(user.getHobby());

            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/user/deleteAll", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllUsers(){
        try{
            userRepository.deleteAll();
            return new ResponseEntity<>("Delete succeeded", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Delete failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id){
        try{
            userRepository.deleteById(id);
            return new ResponseEntity<>("Delete succeeded", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Delete failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
