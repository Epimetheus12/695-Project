package com.worker.people.servicesImpl;

import com.worker.people.domain.entities.Picture;
import com.worker.people.domain.entities.Role;
import com.worker.people.domain.entities.User;
import com.worker.people.domain.models.PictureServiceModel;
import com.worker.people.repositories.PictureRepository;
import com.worker.people.repositories.RoleRepository;
import com.worker.people.repositories.UserRepository;
import com.worker.people.services.CloudinaryService;
import com.worker.people.services.PictureService;
import static com.worker.people.utils.messages.ResponseMessage.*;

import com.worker.people.utils.CustomException;
import com.worker.people.validations.CloudinaryValidation;
import com.worker.people.validations.PictureValidation;
import com.worker.people.validations.UserValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final UserValidation userValidation;
    private final PictureValidation pictureValidation;
    private final CloudinaryValidation cloudinaryValidation;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, UserRepository userRepository,
                              RoleRepository roleRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper, UserValidation userValidation, PictureValidation pictureValidation, CloudinaryValidation cloudinaryValidation) {
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.userValidation = userValidation;
        this.pictureValidation = pictureValidation;
        this.cloudinaryValidation = cloudinaryValidation;
    }

    @Override
    public boolean addPicture(String loggedInUserId, MultipartFile file) throws Exception {
        User user = this.userRepository.findById(loggedInUserId).orElse(null);

        if (!userValidation.isValid(user)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        String cloudinaryPublicId = UUID.randomUUID().toString();
        Map uploadMap = this.cloudinaryService.uploadImage(file, cloudinaryPublicId);

        if (!cloudinaryValidation.isValid(uploadMap)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        Picture picture = new Picture();
        picture.setImageUrl(uploadMap.get("url").toString());
        picture.setUserId(loggedInUserId);
        picture.setTime(LocalDateTime.now());
        picture.setCloudinaryPublicId(uploadMap.get("public_id").toString());

        return this.pictureRepository.save(picture) != null;
    }

    @Override
    public List<PictureServiceModel> getAllPicturesByUserId(String userId) {
        List<Picture> pictureList = this.pictureRepository.findAllByUserId(userId);

        return pictureList
                .stream()
                .map(picture -> this.modelMapper
                        .map(picture, PictureServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deletePicture(String loggedInUserId, String photoToRemoveId) throws Exception {
        User loggedInUser = this.userRepository.findById(loggedInUserId).orElse(null);
        Picture photoToRemove = this.pictureRepository.findById(photoToRemoveId).orElse(null);

        if (!userValidation.isValid(loggedInUser) || !pictureValidation.isValid(photoToRemove)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }


        Role rootRole = this.roleRepository.findByAuthority("ROOT");
        boolean hasRootAuthority = loggedInUser.getAuthorities().contains(rootRole);
        boolean pictureOwnershipCheck = photoToRemove.getUserId().equals(loggedInUserId);

        if (hasRootAuthority || pictureOwnershipCheck) {
            this.pictureRepository.delete(photoToRemove);

            String cloudinaryPublicId = photoToRemove.getCloudinaryPublicId();

            return this.cloudinaryService.deleteImage(cloudinaryPublicId);
        } else {
            throw new CustomException(UNAUTHORIZED_SERVER_ERROR_MESSAGE);
        }
    }
}
