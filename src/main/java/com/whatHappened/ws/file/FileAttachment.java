package com.whatHappened.ws.file;

import com.whatHappened.ws.moment.Moment;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class FileAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attachment_id;

    private String name;

    private String fileType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    private Moment moment;

}
