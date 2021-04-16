package com.worker.people.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.people.domain.entities.User;
import com.worker.people.domain.models.*;
import com.worker.people.repositories.UserRepository;
import com.worker.people.services.UserService;
import com.worker.people.utils.CustomException;
import com.worker.people.utils.responses.BadRequestException;
import com.worker.people.utils.responses.SuccessResponse;
import com.worker.people.validations.UserValidation;
import com.worker.people.validations.serviceValidation.services.UserValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.worker.people.utils.messages.ResponseMessage.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final UserValidation userValidation;
    private final UserRepository userRepository;
    private final UserValidationService userValidationService;


    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, ObjectMapper objectMapper, UserValidation userValidation, UserRepository userRepository,UserValidationService userValidationService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.userValidation= userValidation;
        this.userRepository = userRepository;
        this.userValidationService = userValidationService;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseGet(User::new);
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<User> updateTutorial(@PathVariable("id") String id, @RequestBody User user){
//        Optional<User> userData = userRepository.findById(id);
//
//        if (userData.isPresent()){
//            User _user = userData.get();
//            _user.setUsername(user.getUsername());
//            _user.setEmail(user.getEmail());
//            _user.setGender(user.getGender());
//            _user.setNickname(user.getNickname());
//            _user.setPassword(user.getPassword());
//            _user.setSummary(user.getSummary());
//            _user.setMaritalStatus(user.getMaritalStatus());
//            _user.setBirthday(user.getBirthday());
//            _user.setFirstName(user.getFirstName());
//            _user.setLastName(user.getLastName());
//            _user.setAddress(user.getAddress());
//            _user.setHobby(user.getHobby());
//
//            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
//        }else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UserUpdateModel userUpdateModel,
                                             @PathVariable(value = "id") String loggedInUserId) throws Exception{
        if(!userValidationService.isValid(userUpdateModel)){
            throw new Exception(SERVER_ERROR_MESSAGE);
        }
        UserServiceModel userServiceModel = this.modelMapper.map(userUpdateModel,UserServiceModel.class);
        boolean result = this.userService.updateUser(userServiceModel,loggedInUserId);
        if (result) {
            SuccessResponse successResponse = successResponseBuilder(LocalDateTime.now(), SUCCESSFUL_USER_PROFILE_EDIT_MESSAGE, "", true);
            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }
        throw new CustomException(SERVER_ERROR_MESSAGE);
    }

    @GetMapping(value = "/details/{id}")
    public ResponseEntity<Object> getDatils(@PathVariable String id) throws Exception{
        UserDetailsViewModel user = this.userService.getById(id);
        return new ResponseEntity<>(this.objectMapper.writeValueAsString(user),HttpStatus.OK);
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

}
