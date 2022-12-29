package com.whatHappened.ws.timeline.vm;

import com.whatHappened.ws.moment.vm.MomentVM;
import com.whatHappened.ws.timeline.Timeline;
import lombok.Data;

@Data
public class TimelineMomentVM {

    private String title;

    private String description;

    private long user_id;

    private String creation_date;

    private String[] tags;

    private MomentVM[] moments;

    public TimelineMomentVM(Timeline timeline) {
        this.title = timeline.getTitle();
        this.description = timeline.getDescription();
        this.user_id = timeline.getUser().getUserId();
        this.creation_date = timeline.getCreation_date().toString();

        if(timeline.getTags().length() > 0) {
            this.tags = timeline.getTags().split(",");
        }
    }
}
