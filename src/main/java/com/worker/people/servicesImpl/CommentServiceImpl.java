package com.worker.people.servicesImpl;

import com.worker.people.domain.entities.Comment;
import com.worker.people.domain.entities.Role;
import com.worker.people.domain.entities.Share;
import com.worker.people.domain.entities.User;
import com.worker.people.domain.models.CommentCreateModel;
import com.worker.people.domain.models.CommentServiceModel;
import com.worker.people.repositories.CommentRepository;
import com.worker.people.repositories.RoleRepository;
import com.worker.people.repositories.ShareRepository;
import com.worker.people.repositories.UserRepository;
import com.worker.people.services.CommentService;
import static com.worker.people.utils.messages.ResponseMessage.*;

import com.worker.people.utils.CustomException;
import com.worker.people.validations.CommentValidation;
import com.worker.people.validations.ShareValidation;
import com.worker.people.validations.UserValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ShareRepository shareRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final CommentValidation commentValidation;
    private final UserValidation userValidation;
    private final ShareValidation shareValidation;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ShareRepository shareRepository, RoleRepository roleRepository, ModelMapper modelMapper, CommentValidation commentValidation, UserValidation userValidation, ShareValidation shareValidation) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.shareRepository = shareRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.commentValidation = commentValidation;
        this.userValidation = userValidation;
        this.shareValidation = shareValidation;
    }


    @Override
    public boolean createComment(CommentCreateModel commentCreateBindingModel) throws Exception {
        if (!commentValidation.isValid(commentCreateBindingModel)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        User creator = this.userRepository
                .findById(commentCreateBindingModel.getLoggedInUserId())
                .orElse(null);

        User timelineUser = this.userRepository
                .findById(commentCreateBindingModel.getTimelineUserId())
                .orElse(null);

        Share share = this.shareRepository
                .findById(commentCreateBindingModel.getShareId())
                .orElse(null);

        if (!userValidation.isValid(creator) || !userValidation.isValid(timelineUser) || !shareValidation.isValid(share)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        CommentServiceModel commentServiceModel = new CommentServiceModel();
        commentServiceModel.setShareId(share.getId());
        commentServiceModel.setCreator(creator);
        commentServiceModel.setTimelineUser(timelineUser);
        commentServiceModel.setContent(commentCreateBindingModel.getContent());
        commentServiceModel.setTime(LocalDateTime.now());
        commentServiceModel.setImageUrl(commentCreateBindingModel.getImageUrl());

        Comment comment = this.modelMapper.map(commentServiceModel, Comment.class);

        Comment newComment = this.commentRepository.save(comment);
        share.getCommentList().add(newComment.getId());

        if(newComment.getId() != null){
            shareRepository.save(share);
            return true;
        }
        return false;
    }

    @Async
    @Override
    public CompletableFuture<Boolean> deleteComment(String loggedInUserId, String commentToRemoveId) throws Exception {
        User loggedInUser = this.userRepository.findById(loggedInUserId).orElse(null);
        Comment commentToRemove = this.commentRepository.findById(commentToRemoveId).orElse(null);

        if (!userValidation.isValid(loggedInUser) || !commentValidation.isValid(commentToRemove)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        Role rootRole = this.roleRepository.findByAuthority("ROOT");
        boolean hasRootAuthority = loggedInUser.getAuthorities().contains(rootRole);
        boolean isCommentCreator = commentToRemove.getCreator().getId().equals(loggedInUserId);
        boolean isTimeLineUser = commentToRemove.getTimelineUser().getId().equals(loggedInUserId);

        if (hasRootAuthority || isCommentCreator || isTimeLineUser) {
            try {
                this.commentRepository.delete(commentToRemove);
                //TODO
                return CompletableFuture.completedFuture(true);
            } catch (Exception e) {
                throw new CustomException(SERVER_ERROR_MESSAGE);
            }
        } else {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }
    }
}
