package com.worker.people.servicesImpl;

import com.worker.people.domain.entities.Post;
import com.worker.people.domain.entities.User;
import com.worker.people.domain.models.PostCreateModel;
import com.worker.people.domain.models.PostServiceModel;
import com.worker.people.repositories.PostRepository;
import com.worker.people.repositories.UserRepository;
import com.worker.people.services.PostService;
import com.worker.people.validations.serviceValidation.services.PostValidationService;
import static com.worker.people.utils.messages.ResponseMessage.SERVER_ERROR_MESSAGE;

import com.worker.people.validations.serviceValidation.services.UserValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PostValidationService postValidationService;
    private final UserValidationService userValidationService;

    public PostServiceImpl(PostValidationService postValidationService, PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper, UserValidationService userValidationService) {
        this.postValidationService = postValidationService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userValidationService = userValidationService;
    }


    @Override
    public boolean createPost(PostCreateModel postCreateModel) throws Exception {
        if (!postValidationService.isValid(postCreateModel)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }
        User loggedInUser = this.userRepository
                .findById(postCreateModel.getLoggedInUserId())
                .filter(userValidationService::isValid)
                .orElseThrow(Exception::new);

        User timelineUser = this.userRepository
                .findById(postCreateModel.getTimelineUserId())
                .filter(userValidationService::isValid)
                .orElseThrow(Exception::new);
        PostServiceModel postServiceModel = new PostServiceModel();
        postServiceModel.setLoggedInUser(loggedInUser);
        postServiceModel.setTimelineUser(timelineUser);
        postServiceModel.setContent(postCreateModel.getContent());
        postServiceModel.setImageUrl(postCreateModel.getImageUrl());
        postServiceModel.setTime(LocalDateTime.now());
        postServiceModel.setLike(new ArrayList<>());
        postServiceModel.setCommentList(new ArrayList<>());

        Post post = this.modelMapper.map(postServiceModel, Post.class);

        if (postValidationService.isValid(post)) {
            return this.postRepository.save(post) != null;
        }
        return false;
    }

    @Override
    public List<PostServiceModel> getAllPosts(String timelineUserId) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deletePost(String loggedInUserId, String postToRemoveId) throws Exception {
        return null;
    }
}
