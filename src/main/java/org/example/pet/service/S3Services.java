package org.example.pet.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Services {
    public void downloadFile(String keyName);
    public String uploadFile(String keyName, MultipartFile file);
}
