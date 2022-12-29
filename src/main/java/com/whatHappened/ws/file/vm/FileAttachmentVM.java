package com.whatHappened.ws.file.vm;

import com.whatHappened.ws.file.FileAttachment;
import lombok.Data;

@Data
public class FileAttachmentVM {

    private String name;
    private String fileType;

    public FileAttachmentVM(FileAttachment fileAttachment) {
        this.name = fileAttachment.getName();
        this.fileType = fileAttachment.getFileType();
    }
}
