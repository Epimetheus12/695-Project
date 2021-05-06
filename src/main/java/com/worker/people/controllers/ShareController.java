package com.worker.people.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.people.domain.models.CommentViewModel;
import com.worker.people.domain.models.ShareCreateModel;
import com.worker.people.domain.models.ShareViewModel;
import com.worker.people.services.ShareService;
import com.worker.people.utils.CustomException;
import static com.worker.people.utils.messages.ResponseMessage.*;
import com.worker.people.utils.responses.SuccessResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.worker.people.domain.entities.Share;
import com.worker.people.repositories.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/api")
public class ShareController {

    private final ShareRepository shareRepository;
    private final ShareService shareService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public ShareController(ShareRepository shareRepository, ShareService shareService, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.shareRepository = shareRepository;
        this.shareService = shareService;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    Logger LOG = LoggerFactory.getLogger(getClass());

    @RequestMapping(value="/share/{id}",method = RequestMethod.GET)
    public Share getShareById(@PathVariable("id") String shareId){
        //find share by share id
        LOG.info("Getting Share with ID : {}",shareId);
        Optional<Share> share = shareRepository.findById(shareId);
        return share.orElseGet(Share::new);
    }


    /*public Share save(@RequestBody Share share){
        //save a new share
        shareRepository.save(share);
        return share;
    }*/

    @RequestMapping(method = RequestMethod.POST,value="/share/create")
    public ResponseEntity<Object> createPost(@RequestParam(name = "timelineUserId") String timelineUserId, @RequestParam(name = "loggedInUserId") String loggedInUserId, @RequestParam(name = "content") String content, @RequestParam(name = "len") int len, HttpServletRequest httpServletRequest) throws Exception {

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
        ArrayList<MultipartFile> images = new ArrayList<>();

        for (int i = 0; i < len; i++){
            images.add(multipartHttpServletRequest.getFile("file"+i));
        }

        boolean post = this.shareService.createPost(timelineUserId, loggedInUserId, content, images);

        if (post) {
            SuccessResponse successResponse = new SuccessResponse(LocalDateTime.now(), SUCCESSFUL_CREATE_POST_MESSAGE, " ", true);
            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }

        throw new CustomException(SERVER_ERROR_MESSAGE);
    }
    /*
    @PutMapping(value = "/updateshare/{id}")
    public ResponseEntity<HttpStatus> updateShare(@PathVariable("id") String shareId,@RequestBody Share share){
    //update a share through share id
        Optional<Share> shareData = shareRepository.findById(shareId);
        if(shareData.isPresent()){
            Share _share = shareData.get();
            _share.setContent(share.getContent());
        }

    }
    */

    @RequestMapping(value = "/share/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String shareId){
        // delete a share through share id
        try{
            shareRepository.deleteById(shareId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/share/remove", method = RequestMethod.POST)
    public ResponseEntity removePost(@RequestBody Map<String, Object> body) throws Exception {
        String loggedInUserId = (String) body.get("loggedInUserId");
        String postToRemoveId = (String) body.get("postToRemoveId");

        CompletableFuture<Boolean> result = this.shareService.deletePost(loggedInUserId, postToRemoveId);

        if (result.get()) {
            SuccessResponse successResponse = new SuccessResponse(LocalDateTime.now(), SUCCESSFUL_POST_DELETE_MESSAGE, "", true);
            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }

        throw new CustomException(SERVER_ERROR_MESSAGE);
    }

    /*@RequestMapping(value = "/share/user/{id}", method = RequestMethod.GET)
    public Optional<Share> getShareByUser(@PathVariable("id") String userId){
        //find shares by user id
        return shareRepository.findByAuthorId(userId);
    }*/

    @RequestMapping(value = "/share/all/{id}", method = RequestMethod.GET)
    public List<ShareViewModel> getAllShareById(@PathVariable("id") String id){
        return shareService.getAllShares(id);
    }

    /*@RequestMapping(value = "/share/comment/{id}", method = RequestMethod.GET)
    public List<CommentViewModel> getShareByUser(@PathVariable("id") String id) throws Exception{
        //find shares by user id
        *//*shareService.getComments(id).get(0).getTime()*//*
        return shareService.getComments(id);
    }*/

    @PostMapping(value = "/share/like")
    public ResponseEntity addLike(@RequestBody Map<String, Object> body) throws Exception {
        String shareId = (String) body.get("postId");
        String loggedInUserId = (String) body.get("loggedInUserId");
        boolean result = false;

        if (shareRepository.findById(shareId).isPresent()){
            Share share = shareRepository.findById(shareId).get();
            if(share.getLikeNum() != null){
                if (!share.getLikeNum().contains(loggedInUserId)) {
                    share.getLikeNum().add(loggedInUserId);
                    shareRepository.save(share);
                }
                result = true;
            }else {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(loggedInUserId);
                share.setLikeNum(arrayList);
                shareRepository.save(share);
                result = true;
            }
        }

        if (result) {
            SuccessResponse successResponse = new SuccessResponse(LocalDateTime.now(), SUCCESSFUL_LIKE_POST_MESSAGE, "", true);

            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }

        throw new CustomException(SERVER_ERROR_MESSAGE);
    }
}
