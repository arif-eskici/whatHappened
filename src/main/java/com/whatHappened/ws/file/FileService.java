package com.whatHappened.ws.file;

import com.whatHappened.ws.configuration.AppConfiguration;
import com.whatHappened.ws.user.User;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    FileAttachmentRepository fileAttachmentRepository;
    AppConfiguration appConfiguration;
    Tika tika;

    public FileService(FileAttachmentRepository fileAttachmentRepository, AppConfiguration appConfiguration) {
        super();
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.appConfiguration = appConfiguration;
        this.tika = new Tika();
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void deleteAttachmentFile(String oldImageName) {
        if(oldImageName == null) {
            return;
        }
        deleteFile(Paths.get(appConfiguration.getAttachmentStoragePath(), oldImageName));
    }
    private void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String detectType(byte[] arr) {
        return tika.detect(arr);
    }

    public FileAttachment saveMomentAttachment(MultipartFile multipartFile) {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getAttachmentStoragePath() + "/" + fileName);
        String fileType = null;
        try {
            byte[] arr = multipartFile.getBytes();
            OutputStream outputStream = new FileOutputStream(target);
            outputStream.write(arr);
            outputStream.close();
            fileType = detectType(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileAttachment attachment = new FileAttachment();
        attachment.setName(fileName);
        attachment.setDate(new Date());
        attachment.setFileType(fileType);
        return fileAttachmentRepository.save(attachment);
    }

    public void deleteAllStoredFilesForUser(User inDB) {
        List<FileAttachment> filesToBeRemoved = fileAttachmentRepository.findByMomentTimelineUser(inDB);
        for(FileAttachment file : filesToBeRemoved) {
            deleteAttachmentFile(file.getName());
        }
    }
}
