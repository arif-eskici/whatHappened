package com.whatHappened.ws.moment;

import com.whatHappened.ws.timeline.Timeline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MomentRepository extends JpaRepository<Moment, Long> {

    Page<Moment> findByTimeline(Timeline timeline, Pageable page);

    List<Moment> findByTimeline(Timeline timeline);
}
