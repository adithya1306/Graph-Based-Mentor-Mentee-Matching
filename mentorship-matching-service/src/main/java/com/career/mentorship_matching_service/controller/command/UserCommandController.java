package com.career.mentorship_matching_service.controller.command;

import com.career.mentorship_matching_service.dto.MeetingRequestDTO;
import com.career.mentorship_matching_service.dto.RescheduleMeetingDTO;
import com.career.mentorship_matching_service.model.TimeSlot;
import com.career.mentorship_matching_service.dto.UserCommandDTO;
import com.career.mentorship_matching_service.service.command.UserCommandService;
import org.neo4j.driver.summary.ResultSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commands/users")
public class UserCommandController {
    @Autowired
    private UserCommandService service;

    @PostMapping
    public ResponseEntity<String> saveOrUpdateUser(@RequestBody UserCommandDTO user) {
        service.saveOrUpdateUser(user);
        return ResponseEntity.ok("User saved or updated successfully");
    }

    @PostMapping("/available-slots")
    public ResponseEntity<?> saveAvailableSlots(@RequestBody List<TimeSlot> timeSlots,
                                                @RequestHeader("X-Role") String role,
                                                @RequestHeader("X-User-Id") String userId) {
        if (role.equalsIgnoreCase("MENTOR")) {
            service.saveAvailableSlotsForMentor(userId, timeSlots);
            return ResponseEntity.ok("Available slots saved successfully for mentor");
        } else if (role.equalsIgnoreCase("MENTEE")) {
            service.saveAvailableSlotsForMentee(userId, timeSlots);
            return ResponseEntity.ok("Available slots saved successfully for mentee");
        } else {
            return ResponseEntity.badRequest().body("Invalid role: " + role);
        }
    }

    @PostMapping("/schedule-meeting")
    public ResponseEntity<?> scheduleMeeting(
            @RequestBody MeetingRequestDTO meetingRequest,
            @RequestHeader("X-User-Id") String menteeId
    ) {
        try {
            service.scheduleMeeting(meetingRequest,menteeId);
            return new ResponseEntity<>("Meeting Scheduled Successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reschedule-meeting")
    public ResponseEntity<?> rescheduleMeeting(
            @RequestBody RescheduleMeetingDTO rescheduleRequest,
            @RequestHeader("X-User-Id") String mentorId
    ) {
        try {
            ResultSummary result = (ResultSummary) service.rescheduleMeeting(rescheduleRequest, mentorId);
            if (result.counters().containsUpdates()) {
                return ResponseEntity.ok("Meeting rescheduled successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such meeting found");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cancel-meeting")
    public ResponseEntity<?> cancelMeeting(
            @RequestBody RescheduleMeetingDTO cancelRequest,
            @RequestHeader("X-User-Id") String mentorId
    ) {
        try {
            ResultSummary result = (ResultSummary) service.cancelMeeting(cancelRequest, mentorId);
            if (result.counters().containsUpdates()) {
                return ResponseEntity.ok("Meeting cancelled");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such meeting found");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
