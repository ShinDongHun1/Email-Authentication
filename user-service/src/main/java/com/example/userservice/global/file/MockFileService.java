package com.example.userservice.global.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ShinD on 2022-03-08.
 */
@Service
public class MockFileService implements FileService {
    @Override
    public String saveAndReturnPath(MultipartFile image) {
        return "mock";
    }

    @Override
    public void delete(String filePath) {

    }
}
