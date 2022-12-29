package com.whatHappened.ws.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "what-happened")
public class AppConfiguration {

    private String uploadPath;

    private String attachmentStorage = "attachments";

    public String getAttachmentStoragePath() {
        return uploadPath + "/" + attachmentStorage;
    }

}
