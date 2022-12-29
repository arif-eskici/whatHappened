package com.whatHappened.ws.moment.vm;

import lombok.Data;

@Data
public class MomentSubmitVM {

    private String title;

    private String description;

    private String moment_date;

    private long timeline_id;

    private long[] attachment_id;
}
