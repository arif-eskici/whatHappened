package com.whatHappened.ws.moment;

import com.whatHappened.ws.moment.vm.MomentSubmitVM;
import com.whatHappened.ws.moment.vm.MomentVM;
import com.whatHappened.ws.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1")
public class MomentController {

    @Autowired
    MomentService momentService;

    @PostMapping("/moments")
    GenericResponse saveMoment(@RequestBody MomentSubmitVM moment) throws ParseException {
        momentService.save(moment);
        return new GenericResponse("Moment is saved");
    }

    @GetMapping("/moments")
    Page<MomentVM> getMoments(Pageable page) {
        return momentService.getMoments(page).map(MomentVM::new);
    }

    @GetMapping("/users/{username}/timelines/{id:[0-9]+}/moments")
    Page<MomentVM> getMomentOfTimelineOfUser(Pageable page, @PathVariable String username, @PathVariable long id) {
        return momentService.getMomentsOfTimelineOfUser(page, id).map(MomentVM::new);
    }

    @PutMapping("/users/{username}/moments/{id:[0-9]+}")
    @PreAuthorize("#username == principal.username")
    GenericResponse updateMoment(@RequestBody MomentSubmitVM updatedMoment, @PathVariable String username, @PathVariable long id) throws ParseException {
        momentService.updateMoment(updatedMoment, id);
        return new GenericResponse("Moment is updated");
    }

    @DeleteMapping("/moments/{id:[0-9]+}")
    @PreAuthorize("@momentSecurity.isAllowedToDelete(#id, principal)")
    GenericResponse deleteMoment(@PathVariable long id) {
        momentService.delete(id);
        return new GenericResponse("Moment removed");
    }

}
