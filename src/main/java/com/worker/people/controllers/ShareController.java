package com.worker.people.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.worker.people.domain.entities.Share;
import com.worker.people.repositories.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/api2")
public class ShareController {

    @Autowired
    private ShareRepository shareRepository;

    Logger LOG = LoggerFactory.getLogger(getClass());

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Share getShareById(@PathVariable("id") String shareId){
        //find share by share id
        LOG.info("Getting Share with ID : {}",shareId);
        Optional<Share> share = shareRepository.findById(shareId);
        return share.orElseGet(Share::new);
    }

    @RequestMapping(method = RequestMethod.POST,value="/saveshare")
    public Share save(@RequestBody Share share){
        //save a new share
        shareRepository.save(share);
        return share;
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

    @DeleteMapping(value = "/deleteshare/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String shareId){
        // delete a share through share id
        try{
            shareRepository.deleteById(shareId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{userid}/shares")
    public List<Share> getShareByUser(@PathVariable("userid") String userId){
        //find shares by user id
        return shareRepository.findByAuthorId(userId);
    }

}
