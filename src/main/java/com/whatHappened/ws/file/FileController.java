package com.whatHappened.ws.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/api/v1/moment-attachment")
    FileAttachment saveMomentAttachment(MultipartFile file) {
        return fileService.saveMomentAttachment(file);
    }
}
