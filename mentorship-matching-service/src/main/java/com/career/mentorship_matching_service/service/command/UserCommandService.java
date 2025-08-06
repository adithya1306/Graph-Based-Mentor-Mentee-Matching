package com.career.mentorship_matching_service.service.command;

import com.career.mentorship_matching_service.dto.MeetingRequestDTO;
import com.career.mentorship_matching_service.dto.RescheduleMeetingDTO;
import com.career.mentorship_matching_service.dto.UserCommandDTO;
import com.career.mentorship_matching_service.model.*;
import com.career.mentorship_matching_service.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Data received from kafka
// Received user data: {"id":null,"firstName":"Adithya","lastName":"Balaji","email":"it.adithyab@gmail.com","password":"$2a$12$WHfg8Z7FBYPZzz.Tij2rmurBh8.ujjnStm3zKJCaaS9mDudSZJAR.","phoneNumber":"9876543210","role":"MENTEE","skills":["Java","Spring Boot","REST APIs"],"goals":["Become a backend developer","Contribute to open source"],"experienceLevel":"MID","availability":["Monday 6PM-8PM","Saturday 10AM-12PM"],"verified":false} with key: MENTEE

@Service
public class UserCommandService {
    @Autowired
    private MentorRepo mentorRepo;

    @Autowired
    private MenteeRepo menteeRepo;

    @Autowired
    private TsRepo tsRepo;

    @Autowired
    private MeetingRepo meetingRepo;

    @Autowired
    private MeetingQuery meetingQuery;

    public void saveOrUpdateUser(User user) {
        String role = user.getRole();

        if (role.equalsIgnoreCase("MENTOR")) {
            Mentor mentor = new Mentor(
                    String.valueOf(mentorRepo.findLastUser() + 1),
                    user.getFirstName() + " " + user.getLastName(),
                    user.getEmail(),
                    user.getSkills().stream().map(Skill::new).collect(Collectors.toList()),
                    new Industry(user.getIndustry())
            );
            mentorRepo.save(mentor);
            System.out.println("Added mentor" + user.getFirstName() + " " + user.getLastName());
        } else if (role.equalsIgnoreCase("MENTEE")) {
            System.out.println(user.getSkills().stream().map(Skill::new).collect(Collectors.toList()));
            Mentee mentee = new Mentee(
                    String.valueOf(menteeRepo.findLastUser() + 1),
                    user.getFirstName() + " " + user.getLastName(),
                    user.getEmail(),
                    user.getSkills().stream().map(Skill::new).collect(Collectors.toList()),
                    new Industry(user.getIndustry()));
            menteeRepo.save(mentee);
            System.out.println("Added mentee" + user.getFirstName() + " " + user.getLastName());
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    public Mentee confirmMentor(String menteeId, String mentorId) {
        Mentee mentee = menteeRepo.findById(menteeId).orElseThrow(() -> new IllegalArgumentException("Mentee not found"));
        Mentor mentor = mentorRepo.findById(mentorId).orElseThrow(() -> new IllegalArgumentException("Mentor not found"));

        menteeRepo.deleteMentorRelationship(menteeId);

        mentee.setMentors(List.of(mentor));
        menteeRepo.save(mentee);
        System.out.println(mentee.getMentors());

        return mentee;
    }


    public void saveAvailableSlotsForMentor(String userId, List<TimeSlot> timeSlots) {
        Mentor mentor = mentorRepo.findById(userId).get();

        List<TimeSlot> uniqueTimeSlots = timeSlots.stream()
                .map(ts -> tsRepo.findByDateAndStartTimeAndEndTime(ts.getDate(),ts.getStartTime(), ts.getEndTime())
                        .orElseGet(() -> tsRepo.save(ts)))
                .toList();


        mentor.setAvailableSlots(uniqueTimeSlots);
        mentorRepo.save(mentor);
    }

    public void saveAvailableSlotsForMentee(String userId, List<TimeSlot> timeSlots) {
        Mentee mentee = menteeRepo.findById(userId).get();

        List<TimeSlot> uniqueTimeSlots = timeSlots.stream()
                .map(ts -> tsRepo.findByDateAndStartTimeAndEndTime(ts.getDate(),ts.getStartTime(), ts.getEndTime())
                        .orElseGet(() -> tsRepo.save(ts)))
                .toList();

        mentee.setAvailableSlots(uniqueTimeSlots);
        menteeRepo.save(mentee);
    }

    public Meeting scheduleMeeting(MeetingRequestDTO meetingRequest, String menteeId) {
        Mentor mentor = mentorRepo.findById(meetingRequest.getMentorId()).get();
        Mentee mentee = menteeRepo.findById(menteeId).get();

        // Check if the requested slot is still available
        Optional<TimeSlot> matched = mentor.getAvailableSlots().stream()
                .filter(slot ->
                        slot.getDate().equals(meetingRequest.getDate()) &&
                        slot.getStartTime().equals(meetingRequest.getStartTime()) &&
                        slot.getEndTime().equals(meetingRequest.getEndTime())
                ).findFirst();

        if(matched.isEmpty()) {
            throw new IllegalArgumentException("Mentor does not have the requested time slot");
        }

        Meeting meeting = new Meeting();
        meeting.setDate(meetingRequest.getDate());
        meeting.setStartTime(meetingRequest.getStartTime());
        meeting.setEndTime(meetingRequest.getEndTime());
        meeting.setMentor(mentor);
        meeting.setMentee(mentee);

        meetingRepo.save(meeting);

        // Delete the particular timeslot from the mentor and mentee available slots list
        mentor.getAvailableSlots().remove(matched.get());
        mentorRepo.save(mentor);

        mentee.getAvailableSlots().remove(matched.get());
        menteeRepo.save(mentee);

        return meeting;
    }

    public Object rescheduleMeeting(RescheduleMeetingDTO rescheduleRequest, String mentorId) {
        return meetingQuery.rescheduleMeeting(rescheduleRequest, mentorId);
    }

    public Object cancelMeeting(RescheduleMeetingDTO cancelMeeting, String mentorId) {
        return meetingQuery.cancelMeeting(cancelMeeting, mentorId);
    }
}
