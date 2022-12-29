package com.whatHappened.ws.moment;

import com.whatHappened.ws.file.FileAttachment;
import com.whatHappened.ws.file.FileAttachmentRepository;
import com.whatHappened.ws.file.FileService;
import com.whatHappened.ws.moment.vm.MomentSubmitVM;
import com.whatHappened.ws.moment.vm.MomentVM;
import com.whatHappened.ws.timeline.Timeline;
import com.whatHappened.ws.timeline.TimelineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MomentService {

    MomentRepository momentRepository;
    TimelineRepository timelineRepository;
    FileAttachmentRepository fileAttachmentRepository;
    FileService fileService;

    public MomentService (MomentRepository momentRepository, TimelineRepository timelineRepository,
                          FileAttachmentRepository fileAttachmentRepository, FileService fileService) {
        super();
        this.momentRepository = momentRepository;
        this.timelineRepository = timelineRepository;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.fileService = fileService;
    }


    public void save(MomentSubmitVM momentSubmitVM) throws ParseException {
        Moment moment =  new Moment();
        moment.setTitle(momentSubmitVM.getTitle());
        moment.setDescription(momentSubmitVM.getDescription());
        moment.setCreation_date(new Date());
        Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(momentSubmitVM.getMoment_date().substring(0, 16));
        moment.setMoment_date(date);
        moment.setTimeline(timelineRepository.getReferenceById(momentSubmitVM.getTimeline_id()));
        if(momentSubmitVM.getAttachment_id() != null) {
            for(long attachment_id : momentSubmitVM.getAttachment_id()) {
                Optional<FileAttachment> optionalFileAttachment = fileAttachmentRepository.findById(attachment_id);
                if(optionalFileAttachment.isPresent()) {
                    FileAttachment fileAttachment = optionalFileAttachment.get();
                    fileAttachment.setMoment(moment);
                    fileAttachmentRepository.save(fileAttachment);
                }
            }
        }
        momentRepository.save(moment);
    }

    public Page<Moment> getMoments(Pageable page) {
        return momentRepository.findAll(page);
    }

    public Page<Moment> getMomentsOfTimelineOfUser(Pageable page, long timeline_id) {
        Timeline timeline = timelineRepository.getReferenceById(timeline_id);
        return momentRepository.findByTimeline(timeline, page);
    }

    public List<MomentVM> getMomentsOfTimeline(long timeline_id) {
        Timeline inDB = timelineRepository.getReferenceById(timeline_id);
        List<Moment> moments = momentRepository.findByTimeline(inDB);
        List<MomentVM> momentVM = new ArrayList<>();
        for(int i=0; i<moments.size(); i++) {
            momentVM.add(new MomentVM(moments.get(i)));
        }
        return momentVM;
    }

    public MomentVM updateMoment(MomentSubmitVM updatedMoment, long id) throws ParseException {
        Moment inDB = momentRepository.getReferenceById(id);
        inDB.setTitle(updatedMoment.getTitle());
        inDB.setDescription(updatedMoment.getDescription());
        inDB.setCreation_date(new Date());
        Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(updatedMoment.getMoment_date().substring(0,16));
        inDB.setMoment_date(date);
        if(updatedMoment.getAttachment_id() != null) {
            for(long attachment_id : updatedMoment.getAttachment_id()) {
                Optional<FileAttachment> optionalFileAttachment = fileAttachmentRepository.findById(attachment_id);
                if(optionalFileAttachment.isPresent()) {
                    FileAttachment fileAttachment = optionalFileAttachment.get();
                    fileAttachment.setMoment(inDB);
                    fileAttachmentRepository.save(fileAttachment);
                }
            }
        }
        momentRepository.save(inDB);
        return new MomentVM(inDB);
    }

    public void delete(long id) {
        Moment inDB = momentRepository.getReferenceById(id);
        if(inDB.getAttachments() != null) {
            for(int i=0; i<inDB.getAttachments().size(); i++) {
                String fileName = inDB.getAttachments().get(i).getName();
                fileService.deleteAttachmentFile(fileName);
            }
            momentRepository.deleteById(id);
        }
    }
}
