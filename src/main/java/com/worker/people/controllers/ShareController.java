package com.worker.people.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.people.domain.models.PostCreateModel;
import com.worker.people.utils.CustomException;
import com.worker.people.utils.responses.SuccessResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.worker.people.domain.entities.Share;
import com.worker.people.repositories.ShareRepository;
import com.worker.people.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import static com.worker.people.utils.messages.ResponseMessage.*;
import com.worker.people.repositories.PostRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/api")
public class ShareController {

    @Autowired
    private ShareRepository shareRepository;

    private final PostService postService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    public ShareController(ModelMapper modelMapper, ObjectMapper objectMapper,PostService postService) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.postService = postService;
    }

    @RequestMapping(value="/share/{id}",method = RequestMethod.GET)
    public Share getShareById(@PathVariable("id") String shareId){
        //find share by share id
        LOG.info("Getting Share with ID : {}",shareId);
        Optional<Share> share = shareRepository.findById(shareId);
        return share.orElseGet(Share::new);
    }

    @RequestMapping(method = RequestMethod.POST,value="/share/create")
    public Share save(@RequestBody Share share){
        //save a new share
        shareRepository.save(share);
        return share;
    }

//    @GetMapping(value="/all/{id}")
//    public List<PostAllViewModel> getAllPosts(@PathVariable(value = "id") String timelineUserId){
//        try{
//
//        }
//        catch (Exception e){
//
//        }
//    }

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

    @RequestMapping(value = "/share/user/{id}", method = RequestMethod.GET)
    public List<Share> getShareByUser(@PathVariable("id") String userId){
        //find shares by user id
        return shareRepository.findByAuthorId(userId);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createPost(@RequestBody@Valid PostCreateModel postCreateModel, Authentication principal) throws Exception{
        boolean post = this.postService.createPost(postCreateModel);
        if (post) {
            SuccessResponse successResponse = new SuccessResponse(LocalDateTime.now(), SUCCESSFUL_CREATE_POST_MESSAGE, " ", true);
            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }

        throw new CustomException(SERVER_ERROR_MESSAGE);
    }

}
