package com.worker.people.servicesImpl;

import com.worker.people.domain.entities.Comment;
import com.worker.people.domain.entities.Role;
import com.worker.people.domain.entities.Share;
import com.worker.people.domain.entities.User;
import com.worker.people.domain.models.ShareCreateModel;
import com.worker.people.domain.models.ShareServiceModel;
import com.worker.people.repositories.RoleRepository;
import com.worker.people.repositories.ShareRepository;
import com.worker.people.repositories.UserRepository;
import com.worker.people.services.ShareService;
import static com.worker.people.utils.messages.ResponseMessage.*;
import com.worker.people.validations.ShareValidation;
import com.worker.people.validations.UserValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShareServiceImpl implements ShareService {
    private final ShareRepository postRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final ShareValidation postValidationService;
    private final UserValidation userValidationService;

    @Autowired
    public ShareServiceImpl(ShareRepository postRepository, UserRepository userRepository, /*LikeRepository likeRepository,*/ RoleRepository roleRepository, ModelMapper modelMapper, ShareValidation postValidationService, UserValidation userValidationService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        /*this.likeRepository = likeRepository;*/
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.postValidationService = postValidationService;
        this.userValidationService = userValidationService;
    }

    @Override
    public boolean createPost(ShareCreateModel postCreateBindingModel) throws Exception {
        if (!postValidationService.isValid(postCreateBindingModel)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        User loggedInUser = this.userRepository
                .findById(postCreateBindingModel.getLoggedInUserId())
                .filter(userValidationService::isValid)
                .orElseThrow(Exception::new);

        User timelineUser = this.userRepository
                .findById(postCreateBindingModel.getTimelineUserId())
                .filter(userValidationService::isValid)
                .orElseThrow(Exception::new);

        ShareServiceModel postServiceModel = new ShareServiceModel();
        postServiceModel.setLoggedInUser(loggedInUser);
        postServiceModel.setTimelineUser(timelineUser);
        postServiceModel.setContent(postCreateBindingModel.getContent());
        postServiceModel.setImageUrl(postCreateBindingModel.getImageUrl());
        postServiceModel.setTime(LocalDateTime.now());
//        postServiceModel.s(new ArrayList<>());
//        postServiceModel.setComment(new ArrayList<>());

        Share post = this.modelMapper.map(postServiceModel, Share.class);

        if (postValidationService.isValid(post)) {
            return this.postRepository.save(post) != null;
        }
        return false;
    }

    @Override
    public List<ShareServiceModel> getAllPosts(String timelineUserId) {
        Optional<Share> postList = this.postRepository.findById/*.findAllByTimelineUserIdOrderByTimeDesc*/(timelineUserId);


        return postList
                .stream()
                .map(post -> this.modelMapper
                        .map(post, ShareServiceModel.class))
                /*.peek(postServiceModel -> {
                    List<Comment> commentList = postServiceModel.getCommentList()
                            .stream()
                            .sorted((comment1, comment2) -> {
                                if (comment1.get().isAfter(comment2.getTime())) {
                                    return 1;
                                } else if (comment1.getTime().isBefore(comment2.getTime())) {
                                    return -1;
                                }
                                return 0;
                            }).collect(Collectors.toList());

                    postServiceModel.setCommentList(commentList);
                })*/
                .collect(Collectors.toList());
    }

    @Async
    @Override
    public CompletableFuture<Boolean> deletePost(String loggedInUserId, String postToRemoveId) throws Exception {
        User loggedInUser = this.userRepository.findById(loggedInUserId).orElse(null);
        Share postToRemove = this.postRepository.findById(postToRemoveId).orElse(null);

        if (!userValidationService.isValid(loggedInUser) || !postValidationService.isValid(postToRemove)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        Role rootRole = this.roleRepository.findByAuthority("ROOT");
        boolean hasRootAuthority = loggedInUser.getAuthorities().contains(rootRole);
        boolean isPostCreator = postToRemove.getAuthorId().equals(loggedInUserId);
        boolean isTimeLineUser = postToRemove.getAuthorId().equals(loggedInUserId);

        if (hasRootAuthority || isPostCreator || isTimeLineUser) {
            try {
                this.postRepository.delete(postToRemove);
                return CompletableFuture.completedFuture(true);
            } catch (Exception e) {
                throw new Exception(SERVER_ERROR_MESSAGE);
            }
        } else {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }
    }
}
