package com.whatHappened.ws.file;

import com.whatHappened.ws.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {
    List<FileAttachment> findByMomentTimelineUser(User user);
}
