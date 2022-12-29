package com.whatHappened.ws.timeline.vm;

import com.whatHappened.ws.timeline.Timeline;
import lombok.Data;

@Data
public class TimelineVM {

    private long timeline_id;

    private String title;

    private String description;

    private long user_id;

    private String creationDate;

    private String[] tags;

    public TimelineVM (Timeline timeline) {
        this.timeline_id = timeline.getTimeline_id();
        this.title = timeline.getTitle();
        this.description = timeline.getDescription();
        this.user_id = timeline.getUser().getUserId();
        this.creationDate = timeline.getCreation_date().toString();

        if(timeline.getTags().length() > 0) {
            this.tags = timeline.getTags().split(",");
        }
    }
}
