package com.worker.people.servicesImpl;

import com.mongodb.WriteConcern;
import com.worker.people.domain.entities.Role;
import com.worker.people.domain.entities.User;
import com.worker.people.domain.models.UserCreateViewModel;
import com.worker.people.domain.models.UserDetailsViewModel;
import com.worker.people.domain.models.UserEditViewModel;
import com.worker.people.domain.models.UserServiceModel;
import com.worker.people.repositories.RoleRepository;
import com.worker.people.repositories.UserRepository;
import com.worker.people.services.UserService;
import com.worker.people.utils.CustomException;
import com.worker.people.validations.UserValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.worker.people.utils.messages.ResponseMessage.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserValidation userValidation;
    //TODO
//    private final LoggerService loggerService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, UserValidation userValidation/*, LoggerService loggerService*/) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userValidation = userValidation;
//        this.loggerService = loggerService;
    }

    //TODO
    /*@PostConstruct
    public void isOnlineSetup(){
        if(this.userRepository.count() > 0){
            this.userRepository.setIsOnlineToFalse();
        }
    }*/

    @Override
    public UserCreateViewModel createUser(UserServiceModel userServiceModel) {
//        this.loggerService.createLog("POST", userServiceModel.getUsername(), "users", "register");

        if (!this.userValidation.isValid(userServiceModel)) {
            throw new CustomException(SERVER_ERROR_MESSAGE);
        }

        User userEntity = this.modelMapper.map(userServiceModel, User.class);

        userEntity.setPassword(this.bCryptPasswordEncoder
                .encode(userEntity.getPassword()));

        Role userRole = this.roleRepository.findByAuthority("USER");
        Role rootRole = this.roleRepository.findByAuthority("ROOT");
        Set<Role> roles = new HashSet<>();
        if (this.userRepository.findAll().isEmpty()) {
            roles.add(rootRole);
        } else {
            roles.add(userRole);
        }

        userEntity.setAuthorities(roles);
        userEntity.setFollowed(new ArrayList<>());
        userEntity.setFollower(new ArrayList<>());
        User user = this.userRepository.save(userEntity);
        if (user != null) {
            return this.modelMapper.map(userEntity, UserCreateViewModel.class);
        }

        throw new CustomException(SERVER_ERROR_MESSAGE);
    }

    @Override
    public boolean updateUser(UserServiceModel userServiceModel, String loggedInUserId) throws Exception {
        if (!userValidation.isValid(userServiceModel)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        User userToEdit = this.userRepository.findById(userServiceModel.getId()).orElse(null);
        User loggedInUser = this.userRepository.findById(loggedInUserId).orElse(null);

        if (!userValidation.isValid(userToEdit) || !userValidation.isValid(loggedInUser)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        if (!userServiceModel.getId().equals(loggedInUserId)) {
            String userAuthority = this.getUserAuthority(loggedInUserId);
            if (!("ROOT").equals(userAuthority) && !("ADMIN").equals(userAuthority)) {
                throw new CustomException(UNAUTHORIZED_SERVER_ERROR_MESSAGE);
            }
        }

        if(userToEdit != null){
            userToEdit.setFirstName(userServiceModel.getFirstName());
            userToEdit.setLastName(userServiceModel.getLastName());
            userToEdit.setAddress(userServiceModel.getAddress());
            userToEdit.setCity(userServiceModel.getCity());
            userToEdit.setProfilePicURL(userServiceModel.getProfilePicURL());
            userToEdit.setBackgroundPicURL(userServiceModel.getBackgroundPicURL());
        }

        return this.userRepository.save(userToEdit) != null;
    }

//    @Override
//    public UserServiceModel updateUserOnlineStatus(String userName, boolean changeToOnline) throws Exception {
//        User user = this.userRepository.findByUsername(userName)
//                .filter(userValidation::isValid)
//                .orElseThrow(() -> new CustomException(SERVER_ERROR_MESSAGE));
//
//        //TODO
////        if(changeToOnline){
////            user.setOnline(true);
////        }else {
////            user.setOnline(false);
////        }
//
//        User updatedUser = this.userRepository.save(user);
//
//        if (updatedUser != null) {
//            return this.modelMapper.map(updatedUser, UserServiceModel.class);
//        }
//
//        throw new CustomException(SERVER_ERROR_MESSAGE);
//    }


    @Override
    public List<UserServiceModel> getAllUsers(String userId) throws Exception {
        User userById = this.userRepository.findById(userId).orElse(null);

        if (!userValidation.isValid(userById)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        List<Role> Roles = this.getUserRoles(userById);

        if (Roles.size() > 0) {
            return this.userRepository
                    .findAll()
                    .stream()
                    .map(x -> this.modelMapper.map(x, UserServiceModel.class))
                    .collect(Collectors.toList());
        }

        throw new CustomException(UNAUTHORIZED_SERVER_ERROR_MESSAGE);
    }

    @Override
    public UserDetailsViewModel getById(String id) throws Exception {
        User user = this.userRepository.findById(id)
                .filter(userValidation::isValid)
                .orElseThrow(Exception::new);

        return this.modelMapper.map(user, UserDetailsViewModel.class);
    }

    @Override
    public UserEditViewModel editById(String id) throws Exception {
        User user = this.userRepository.findById(id)
                .filter(userValidation::isValid)
                .orElseThrow(Exception::new);

        return this.modelMapper.map(user, UserEditViewModel.class);
    }

    @Override
    public User getByEmailValidation(String email) {
        return this.userRepository.findByEmail(email).orElseGet(User::new);
    }

    @Override
    public User getByUsernameValidation(String username) {
        Optional<User> user = this.userRepository.findByUsername(username);
        return user.orElseGet(User::new);
    }

    @Override
    public boolean deleteUserById(String id) throws Exception {
        this.userRepository.findById(id)
                .filter(userValidation::isValid)
                .orElseThrow(Exception::new);

        try {
            this.userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .filter(userValidation::isValid)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE));
    }

    //todo
//    @Override
//    public boolean promoteUser(String id) throws Exception {
//        User user = this.userRepository.findById(id)
//                .filter(userValidation::isValid)
//                .orElseThrow(Exception::new);
//
//        String userAuthority = this.getUserAuthority(user.getId());
//
//        switch (userAuthority) {
//            case "USER":
//                user.setAuthorities(this.getAuthorities("ADMIN"));
//                break;
//            case "ROOT":
//                throw new CustomException(USER_FAILURE_CHANGING_ROOT_AUTHORITY_MESSAGE);
//            default:
//                throw new CustomException(USER_FAILURE_PROMOTING_ADMIN_MESSAGE);
//        }
//
//        return this.userRepository.save(user) != null;
//    }
//
//    @Override
//    public boolean demoteUser(String id) throws Exception {
//        User user = this.userRepository.findById(id)
//                .filter(userValidation::isValid)
//                .orElseThrow(Exception::new);
//
//        String userAuthority = this.getUserAuthority(user.getId());
//
//        switch (userAuthority) {
//            case "ADMIN":
//                user.setAuthorities(this.getAuthorities("USER"));
//                break;
//            case "ROOT":
//                throw new CustomException(USER_FAILURE_CHANGING_ROOT_AUTHORITY_MESSAGE);
//            case "USER":
//                throw new CustomException(USER_FAILURE_DEMOTING_USER_MESSAGE);
//        }
//
//        return this.userRepository.save(user) != null;
//    }

    private List<Role> getUserRoles(User userById) {
        return userById
                .getAuthorities()
                .stream().filter(userRole ->
                        userRole.getAuthority().equals("ROOT")
                                || userRole.getAuthority().equals("ADMIN"))
                .collect(Collectors.toList());
    }

    private Set<Role> getAuthorities(String authority) {
        Set<Role> userAuthorities = new HashSet<>();

        userAuthorities.add(this.roleRepository.getByAuthority(authority));

        return userAuthorities;
    }

    private String getUserAuthority(String userId) {
        return this
                .userRepository
                .findById(userId)
                .get()
                .getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();
    }
}
