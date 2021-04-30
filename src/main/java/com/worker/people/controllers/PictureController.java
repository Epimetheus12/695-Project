package com.worker.people.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.people.domain.models.PictureViewModel;
import com.worker.people.services.PictureService;
import com.worker.people.utils.CustomException;
import com.worker.people.utils.responses.SuccessResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.worker.people.utils.messages.ResponseMessage.*;

@RestController
@RequestMapping(value = "/api/picture")
public class PictureController {
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public PictureController(PictureService pictureService, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/all/{id}")
    public List<PictureViewModel> getAllPictures(@PathVariable(value = "id") String userId) {
        try {
            return this.pictureService
                    .getAllPicturesByUserId(userId)
                    .stream()
                    .map(x -> this.modelMapper.map(x, PictureViewModel.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException(SERVER_ERROR_MESSAGE);
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Object> addPicture(
            @RequestParam(name = "loggedInUserId") String loggedInUserId,
            @RequestParam(name = "file") MultipartFile file
    ) throws Exception {

        boolean result = this.pictureService.addPicture(loggedInUserId, file);

        if (result) {
            SuccessResponse successResponse = new SuccessResponse(LocalDateTime.now(), SUCCESSFUL_PICTURE_UPLOAD_MESSAGE, "", true);
            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }

        throw new CustomException(SERVER_ERROR_MESSAGE);
    }

    @PostMapping(value = "/remove")
    public ResponseEntity<Object> removePicture(@RequestBody Map<String, Object> body) throws Exception {
        String loggedInUserId = (String) body.get("loggedInUserId");
        String photoToRemoveId = (String) body.get("photoToRemoveId");

        boolean result = this.pictureService.deletePicture(loggedInUserId, photoToRemoveId);

        if (result) {
            SuccessResponse successResponse = new SuccessResponse(LocalDateTime.now(), SUCCESSFUL_PICTURE_DELETE_MESSAGE, "", true);
            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }

        throw new CustomException(SERVER_ERROR_MESSAGE);
    }
}
