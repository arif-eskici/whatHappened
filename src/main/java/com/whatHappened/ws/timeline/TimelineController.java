package com.whatHappened.ws.timeline;

import com.whatHappened.ws.shared.CurrentUser;
import com.whatHappened.ws.shared.GenericResponse;
import com.whatHappened.ws.timeline.vm.TimelineMomentVM;
import com.whatHappened.ws.timeline.vm.TimelineSubmitVM;
import com.whatHappened.ws.timeline.vm.TimelineVM;
import com.whatHappened.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TimelineController {

    @Autowired
    TimelineService timelineService;

    @PostMapping("/timelines")
    GenericResponse saveTimeline (@RequestBody TimelineSubmitVM timeline, @CurrentUser User user) {
        timelineService.save(timeline, user);
        return new GenericResponse("Timeline is saved");
    }

    @GetMapping("/timelines")
    Page<TimelineVM> getTimelines (Pageable page) {
        return timelineService.getTimelines(page).map(TimelineVM::new);
    }

    @GetMapping("/timelines/{id:[0-9]+}")
    TimelineMomentVM getTimelineMoments(@PathVariable long id) {
        return timelineService.getTimelineMoments(id);
    }

    @PutMapping("/{username}/timelines/{id:[0-9]+}")
    @PreAuthorize(("#username == principal.username"))
    GenericResponse updateTimeline(@RequestBody TimelineSubmitVM updatedTimeline, @PathVariable String username, @PathVariable long id) {
        timelineService.updateTimeline(updatedTimeline, id);
        return new GenericResponse("Timeline is updated");
    }

    @DeleteMapping("/timelines/{id:[0-9]+}")
    @PreAuthorize("@timelineSecurity.isAllowedToDelete(#id, principal)")
    GenericResponse deleteTimeline (@PathVariable long id) {
        timelineService.delete(id);
        return new GenericResponse("Timeline removed");
    }

}
