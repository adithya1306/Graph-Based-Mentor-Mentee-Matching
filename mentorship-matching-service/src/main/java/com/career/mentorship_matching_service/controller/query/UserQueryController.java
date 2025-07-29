package com.career.mentorship_matching_service.controller.query;

import com.career.mentorship_matching_service.dto.OverlapTimeSlot;
import com.career.mentorship_matching_service.model.*;
import com.career.mentorship_matching_service.service.query.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/queries/users")
public class UserQueryController {

    @Autowired
    private UserQueryService userQueryService;

    @GetMapping("/mentors/all")
    public ResponseEntity<?> getAllMentors(
            @RequestHeader("X-Role") String role
    ) {
        if(role.equalsIgnoreCase("admin")) {
            return ResponseEntity.ok(userQueryService.getAllMentors());
        } else {
            return new ResponseEntity<>("Only Admins can access this endpoint", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/mentees/all")
    public ResponseEntity<?> getAllMentees(
            @RequestHeader("X-Role") String role
    ) {
        if(role.equalsIgnoreCase("admin")) {
            return ResponseEntity.ok(userQueryService.getAllMentees());
        } else {
            return new ResponseEntity<>("Only Admins can access this endpoint", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/assign-mentors/{menteeId}")
    public Mentee assignTop3Mentors(@PathVariable String menteeId) {
        return userQueryService.assignTop3Mentors(menteeId);
    }

    @GetMapping("/meeting-slots")
    public ResponseEntity<?> getAvailableMeetingSlots(
            @RequestHeader("X-User-Id") String userId
    ) {
        List<Mentor> mentors = userQueryService.getMentorsByMenteeId(userId);
        Set<OverlapTimeSlot> matched = new HashSet<>();
        Set<OverlapTimeSlot> unmatched = new HashSet<>();

        for(Mentor mentor : mentors) {
            HashSet<OverlapTimeSlot> commonTimeSlot = userQueryService.findCommonTimeSlot(mentor, userQueryService.getMenteeById(userId));
            for(OverlapTimeSlot slot : commonTimeSlot){
                if(slot.isMatch()) {
                    matched.add(slot);
                } else {
                    unmatched.add(slot);
                }
            }
        }

        if(matched.isEmpty()) {
            return ResponseEntity.ok(unmatched);
        }

        return ResponseEntity.ok(matched);
    }
}
