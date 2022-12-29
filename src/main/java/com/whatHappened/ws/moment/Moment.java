package com.whatHappened.ws.moment;

import com.whatHappened.ws.file.FileAttachment;
import com.whatHappened.ws.timeline.Timeline;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Moment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long moment_id;

    private String title;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date moment_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;

    @OneToMany(mappedBy = "moment", cascade = CascadeType.REMOVE)
    private List<FileAttachment> attachments;

    @ManyToOne
    private Timeline timeline;
}
