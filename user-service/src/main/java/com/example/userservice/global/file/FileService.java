package com.example.userservice.global.file;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ShinD on 2022-03-08.
 */
public interface FileService {
    String saveAndReturnPath(MultipartFile image);
    void delete(String filePath);
}
