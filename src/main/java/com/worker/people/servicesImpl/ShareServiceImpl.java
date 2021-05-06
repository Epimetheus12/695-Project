package com.worker.people.servicesImpl;

import com.worker.people.domain.entities.Comment;
import com.worker.people.domain.entities.Role;
import com.worker.people.domain.entities.Share;
import com.worker.people.domain.entities.User;
import com.worker.people.domain.models.*;
import com.worker.people.repositories.CommentRepository;
import com.worker.people.repositories.RoleRepository;
import com.worker.people.repositories.ShareRepository;
import com.worker.people.repositories.UserRepository;
import com.worker.people.services.CloudinaryService;
import com.worker.people.services.ShareService;
import static com.worker.people.utils.messages.ResponseMessage.*;

import com.worker.people.validations.CloudinaryValidation;
import com.worker.people.validations.CommentValidation;
import com.worker.people.validations.ShareValidation;
import com.worker.people.validations.UserValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShareServiceImpl implements ShareService {
    private final ShareRepository shareRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final ShareValidation shareValidationService;
    private final UserValidation userValidationService;
    private final CloudinaryService cloudinaryService;
    private final CloudinaryValidation cloudinaryValidation;
    private final CommentRepository commentRepository;
    /*private final CommentValidation commentValidation;*/

    @Autowired
    public ShareServiceImpl(/*CommentValidation commentValidation, */CommentRepository commentRepository, ShareRepository shareRepository, UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, ShareValidation shareValidationService, UserValidation userValidationService, CloudinaryService cloudinaryService, CloudinaryValidation cloudinaryValidation) {
        /*this.commentValidation = commentValidation;*/
        this.commentRepository = commentRepository;
        this.shareRepository = shareRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.shareValidationService = shareValidationService;
        this.userValidationService = userValidationService;
        this.cloudinaryService = cloudinaryService;
        this.cloudinaryValidation = cloudinaryValidation;
    }

    @Override
    public boolean createPost(String timelineUserId, String loggedInUserId, String content, ArrayList<MultipartFile> datas) throws Exception {
        /*if (!shareValidationService.isValid(shareCreateModel)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }*/

        ArrayList<String> images = new ArrayList<>();

        for(int i = 0; i < datas.size(); i++){
            String cloudinaryPublicId = UUID.randomUUID().toString();
            Map uploadMap = this.cloudinaryService.uploadImage(datas.get(i), cloudinaryPublicId);
            images.add(uploadMap.get("url").toString());
        }



        User loggedInUser = this.userRepository
                .findById(loggedInUserId)
                .filter(userValidationService::isValid)
                .orElseThrow(Exception::new);

        /*User timelineUser = this.userRepository
                .findById(shareCreateModel.getTimelineUserId())
                .filter(userValidationService::isValid)
                .orElseThrow(Exception::new);*/

        Share share = new Share();
        share.setAuthorId(loggedInUserId);
        share.setAuthor(loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
        share.setContent(content);
        share.setTime(LocalDateTime.now());
        share.setCommentList(new ArrayList<>());
        share.setImageUrls(images);
        share.setLikeNum(new ArrayList<>());
//        postServiceModel.s(new ArrayList<>());
//        postServiceModel.setComment(new ArrayList<>());


        if (shareValidationService.isValid(share)) {
            return this.shareRepository.save(share) != null;
        }
        return false;
    }

    @Override
    public List<ShareViewModel> getAllShares(String timelineUserId) {

        List<String> allFollowed = userRepository.findById(timelineUserId).orElseThrow().getFollowed();
        allFollowed.add(timelineUserId);
        List<Share> shareList = shareRepository.findAllByAuthorIdInOrderByTimeDesc(allFollowed);



        return shareList
                .stream()
                .map(share -> {
                    ShareViewModel shareViewModel = this.modelMapper.map(share, ShareViewModel.class);
                    shareViewModel.setId(share.getId());
                    shareViewModel.setLikeNum(share.getLikeNum());
                    shareViewModel.setTimelineUserId(timelineUserId);
                    User user = userRepository.findUserById(share.getAuthorId());
                    shareViewModel.setLoggedInUserProfilePicUrl(user.getProfilePicURL());
                    List<String> commentIds = share.getCommentList();
                    List<Comment> commentList = new ArrayList<>();

                    for(int i = 0; i < commentIds.size(); i++){
                        if(commentRepository.findById(commentIds.get(i)).isPresent()){
                            commentList.add(commentRepository.findById(commentIds.get(i)).get());
                        }
                    }

                    /*System.out.println("test============"+commentList);*/

                   List<CommentViewModel> commentViewList = commentList.
                            stream().
                            map(comment -> {
                                CommentViewModel commentViewModel = this.modelMapper.map(comment, CommentViewModel.class);
                                commentViewModel.setCommentId(comment.getId());
                                commentViewModel.setPostId(comment.getShare().getId());
                                commentViewModel.setCreatorId(comment.getCreator().getId());
                                commentViewModel.setCreatorFirstName(comment.getCreator().getFirstName());
                                commentViewModel.setCreatorLastName(comment.getCreator().getLastName());
                                commentViewModel.setCreatorProfilePicUrl(comment.getCreator().getProfilePicURL());
                                commentViewModel.setTimelineUserId(comment.getTimelineUser().getId());
                                return commentViewModel;
                            }).collect(Collectors.toList());
                    shareViewModel.setCommentViewList(commentViewList);
                    return shareViewModel;})
                .collect(Collectors.toList());
    }

    @Async
    @Override
    public CompletableFuture<Boolean> deletePost(String loggedInUserId, String postToRemoveId) throws Exception {
        User loggedInUser = this.userRepository.findById(loggedInUserId).orElse(null);
        Share postToRemove = this.shareRepository.findById(postToRemoveId).orElse(null);

        if (!userValidationService.isValid(loggedInUser) || !shareValidationService.isValid(postToRemove)) {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        Role rootRole = this.roleRepository.findByAuthority("ROOT");
        boolean hasRootAuthority = loggedInUser.getAuthorities().contains(rootRole);
        boolean isPostCreator = postToRemove.getAuthorId().equals(loggedInUserId);
        boolean isTimeLineUser = postToRemove.getAuthorId().equals(loggedInUserId);
        //TODO
        if (hasRootAuthority || isPostCreator || isTimeLineUser) {
            try {
                this.shareRepository.delete(postToRemove);
                return CompletableFuture.completedFuture(true);
            } catch (Exception e) {
                throw new Exception(SERVER_ERROR_MESSAGE);
            }
        } else {
            throw new Exception(SERVER_ERROR_MESSAGE);
        }
    }

    /*@Override
    public List<CommentViewModel> getComments(String shareId) throws Exception{
        Optional<Share> share = shareRepository.findById(shareId);

        List<String> commentIds = share.filter(shareValidationService::isValid).orElseThrow(Exception::new).getCommentList();
        List<Comment> commentList = new ArrayList<>();

        for(int i = 0; i < commentIds.size(); i++){
            if(commentRepository.findById(commentIds.get(i)).isPresent()){
                commentList.add(commentRepository.findById(commentIds.get(i)).get());
            }
        }

        System.out.println("test============"+commentList);


        return commentList.
                stream().
                map(comment -> {
                    CommentViewModel commentViewModel = this.modelMapper.map(comment, CommentViewModel.class);
                    commentViewModel.setCommentId(comment.getId());
                    commentViewModel.setPostId(comment.getShare().getId());
                    commentViewModel.setCreatorId(comment.getCreator().getId());
                    commentViewModel.setCreatorFirstName(comment.getCreator().getFirstName());
                    commentViewModel.setCreatorLastName(comment.getCreator().getLastName());
                    commentViewModel.setCreatorProfilePicUrl(comment.getCreator().getProfilePicURL());
                    commentViewModel.setTimelineUserId(comment.getTimelineUser().getId());
                    return commentViewModel;
                }).collect(Collectors.toList());
    }*/
}
