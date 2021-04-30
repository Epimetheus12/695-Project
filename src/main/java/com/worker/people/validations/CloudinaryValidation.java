package com.worker.people.validations;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public interface CloudinaryValidation {
    boolean isValid(Map uploadMap);

    boolean isValid(MultipartFile file, String uuid);

    boolean isValid(String public_id);
}
