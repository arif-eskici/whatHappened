package com.whatHappened.ws.moment.vm;

import com.whatHappened.ws.file.FileAttachment;
import com.whatHappened.ws.moment.Moment;
import com.whatHappened.ws.user.vm.UserVM;
import lombok.Data;

@Data
public class MomentVM {

    private long id;

    private String content;

    private long timestamp;

    private UserVM user;

    private FileAttachment fileAttachment;

    public MomentVM (Moment moment) {
        this.setId(moment.getMoment_id());

    }
}
