package com.worker.people.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.people.domain.entities.User;
import com.worker.people.domain.models.*;
import com.worker.people.repositories.UserRepository;
import com.worker.people.services.UserService;
import com.worker.people.utils.CustomException;
import com.worker.people.utils.responses.BadRequestException;
import com.worker.people.utils.responses.SuccessResponse;
import com.worker.people.validations.UserValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.worker.people.utils.messages.ResponseMessage.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final UserValidation userValidation;
    private final UserRepository userRepository;


    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, ObjectMapper objectMapper, UserValidation userValidation, UserRepository userRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.userValidation= userValidation;
        this.userRepository = userRepository;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*@RequestMapping(method = RequestMethod.POST, value = "/create")
    public User save(@RequestBody User user){
        userRepository.save(user);

        return user;
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterModel userRegisterModel) throws Exception {

        if (!userValidation.isValid(userRegisterModel.getPassword(), userRegisterModel.getConfirmPassword())) {
            throw new BadRequestException(PASSWORDS_MISMATCH_ERROR_MESSAGE);
        }

        if (!userValidation.isValid(userRegisterModel)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        UserServiceModel user = modelMapper.map(userRegisterModel, UserServiceModel.class);
        UserCreateViewModel savedUser = this.userService.createUser(user);

        SuccessResponse successResponse = successResponseBuilder(LocalDateTime.now(), SUCCESSFUL_REGISTER_MESSAGE, savedUser, true);

        return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserById(@PathVariable String id) throws Exception {
        UserDetailsViewModel user = this.userService.getById(id);
        return new ResponseEntity<>(this.objectMapper.writeValueAsString(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateTutorial(@RequestBody @Valid UserUpdateModel userUpdateModel,
                                               @PathVariable(value = "id") String loggedInUserId) throws Exception{/*
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()){
            User _user = userData.get();
            _user.setUsername(user.getUsername());
            _user.setEmail(user.getEmail());
            _user.setGender(user.getGender());
            _user.setNickname(user.getNickname());
            _user.setPassword(user.getPassword());
            _user.setSummary(user.getSummary());
            _user.setMaritalStatus(user.getMaritalStatus());
            _user.setBirthday(user.getBirthday());
            _user.setFirstName(user.getFirstName());
            _user.setLastName(user.getLastName());
            _user.setAddress(user.getAddress());
            _user.setHobby(user.getHobby());

            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/
        if(!userValidation.isValid(userUpdateModel)){
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userUpdateModel, UserServiceModel.class);
        /*System.out.println(userServiceModel+ "======================"+loggedInUserId);*/
        boolean result = this.userService.updateUser(userServiceModel, loggedInUserId);

        if (result) {
            SuccessResponse successResponse = successResponseBuilder(LocalDateTime.now(), SUCCESSFUL_USER_PROFILE_EDIT_MESSAGE, "", true);
            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }

        throw new CustomException(SERVER_ERROR_MESSAGE);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllUsers(){
        try{
            userRepository.deleteAll();
            return new ResponseEntity<>("Delete succeeded", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Delete failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id){
        try{
            userRepository.deleteById(id);
            return new ResponseEntity<>("Delete succeeded", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Delete failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/findByUsername/{username}", method =RequestMethod.GET)
    public  ResponseEntity<User> getUserByUsername(@PathVariable("username") String username){
        try{
            Optional<User> user = userRepository.findByUsername(username);
            return new ResponseEntity<>(user.orElseGet(User::new), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private SuccessResponse successResponseBuilder(LocalDateTime timestamp, String message, Object payload, boolean success) {
        return new SuccessResponse(timestamp, message, payload, success);
    }

    @RequestMapping(value = "/search", produces = "application/json", method = RequestMethod.POST)
    public List<FriendsCandidatesViewModel> searchUsers(@RequestBody Map<String, Object> body) {
        String loggedInUserId = (String) body.get("loggedInUserId");
        String search = (String) body.get("search");

        List<User> users = userRepository.findByUsernameOrFirstNameOrLastNameOrNicknameLike(search, search, search, search);

        return users.stream().
                filter(user -> !user.getId().equals(loggedInUserId)).
                map(user -> this.modelMapper.map(user, FriendsCandidatesViewModel.class)).
                collect(Collectors.toList());
    }

}
