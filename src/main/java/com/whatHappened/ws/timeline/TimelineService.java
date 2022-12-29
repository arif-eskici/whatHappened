package com.whatHappened.ws.timeline;

import com.whatHappened.ws.moment.MomentService;
import com.whatHappened.ws.moment.vm.MomentVM;
import com.whatHappened.ws.timeline.vm.TimelineMomentVM;
import com.whatHappened.ws.timeline.vm.TimelineSubmitVM;
import com.whatHappened.ws.timeline.vm.TimelineVM;
import com.whatHappened.ws.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TimelineService {
    TimelineRepository timelineRepository;
    MomentService momentService;


    public TimelineService(TimelineRepository timelineRepository, MomentService momentService) {
        super();
        this.timelineRepository = timelineRepository;
        this.momentService = momentService;
    }

    public TimelineVM save (TimelineSubmitVM timelineSubmitVM, User user) {
        Timeline timeline = new Timeline();
        timeline.setTitle(timelineSubmitVM.getTitle());
        timeline.setDescription(timelineSubmitVM.getDescription());
        String tags = "";

        for(String tag : timelineSubmitVM.getTags()) {
            tags += tag + ",";
        }

        timeline.setTags(tags);
        timeline.setUser(user);
        timeline.setCreation_date(new Date());
        timelineRepository.save(timeline);
        return new TimelineVM(timeline);
    }

    public Page<Timeline> getTimelines(Pageable page) {
        return timelineRepository.findAll(page);
    }

    public TimelineVM updateTimeline(TimelineSubmitVM updatedTimeline, long id) {
        Timeline inDB = timelineRepository.getReferenceById(id);
        inDB.setTitle(updatedTimeline.getTitle());
        inDB.setDescription(updatedTimeline.getDescription());
        String tags = "";

        for(String tag : updatedTimeline.getTags()) {
            tags += tag + ",";
        }

        inDB.setTags(tags);
        inDB.setCreation_date(new Date());
        timelineRepository.save(inDB);
        return new TimelineVM(inDB);
    }

    public TimelineMomentVM getTimelineMoments(long id) {
        TimelineMomentVM timelineMomentVM = new TimelineMomentVM(timelineRepository.getReferenceById(id));
        List<MomentVM> momentVMList = momentService.getMomentsOfTimeline(id);
        MomentVM[] momentVM = new MomentVM[momentVMList.size()];
        momentVMList.toArray(momentVM);
        timelineMomentVM.setMoments(momentVM);
        return timelineMomentVM;
    }

    public void delete(long id) {
        timelineRepository.deleteById(id);
    }
}
