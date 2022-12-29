package com.whatHappened.ws.timeline;

import com.whatHappened.ws.moment.Moment;
import com.whatHappened.ws.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long timeline_id;

    private String title;

    private  String description;

    @OneToMany(mappedBy = "timeline", cascade = CascadeType.REMOVE)
    private List<Moment> moments;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;

    private String tags;

    @ManyToOne
    private User user;

}
