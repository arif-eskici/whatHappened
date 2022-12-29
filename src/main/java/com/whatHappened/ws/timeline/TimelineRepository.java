package com.whatHappened.ws.timeline;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {
}
