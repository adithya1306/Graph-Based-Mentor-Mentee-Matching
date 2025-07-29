package com.career.mentorship_matching_service.service.query;

import com.career.mentorship_matching_service.dto.OverlapTimeSlot;
import com.career.mentorship_matching_service.dto.UserQueryDTO;
import com.career.mentorship_matching_service.model.*;
import com.career.mentorship_matching_service.repo.MenteeRepo;
import com.career.mentorship_matching_service.repo.MentorRepo;
import com.career.mentorship_matching_service.repo.UserQueryRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserQueryService {
    @Autowired
    private MentorRepo mentorRepo;

    @Autowired
    private MenteeRepo menteeRepo;

    @Autowired
    private UserQueryRepositoryCustom userQueryRepositoryCustom;

    public List<UserQueryDTO> getAllMentors() {
        List<Mentor> mentors =  mentorRepo.findAll();
        return mentors.stream()
                .map(user -> new UserQueryDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getSkills().stream().toList(),
                        user.getIndustry().getName()
                ))
                .toList();
    }

    public List<UserQueryDTO> getAllMentees() {
        List<Mentee> mentees =  menteeRepo.findAll();
        return mentees.stream()
                .map(user -> new UserQueryDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getSkills().stream().toList(),
                        user.getIndustry().getName()
                ))
                .toList();
    }


    public Mentee assignTop3Mentors(String menteeId) {
        Mentee mentee = menteeRepo.findById(menteeId)
                .orElseThrow(() -> new RuntimeException("Mentee not found with id: " + menteeId));

        List<Mentor> topMentors = userQueryRepositoryCustom.findTop3MentorsForMentee(mentee);

        if (topMentors.isEmpty()) {
            throw new RuntimeException("No mentors found for mentee with id: " + menteeId);
        }

        mentee.setMentors(topMentors);

        return menteeRepo.save(mentee);
    }

    public List<Mentor> getMentorsByMenteeId(String menteeId) {
        Mentee mentee = menteeRepo.findById(menteeId).get();
        return mentee.getMentors();
    }

    public Mentee getMenteeById(String menteeId) {
        return menteeRepo.findById(menteeId)
                .orElseThrow(() -> new RuntimeException("Mentee not found with id: " + menteeId));
    }

    public HashSet<OverlapTimeSlot> findCommonTimeSlot(Mentor mentor, Mentee mentee) {
        Set<OverlapTimeSlot> overlappingSlots = new HashSet<>();
        Set<OverlapTimeSlot> mentorAvailableSlots = new HashSet<>();

        for(TimeSlot mentorSlot : mentor.getAvailableSlots()) {
            for (TimeSlot menteeSlot : mentee.getAvailableSlots()) {
                OverlapTimeSlot overlapTimeSlot = new OverlapTimeSlot();
                overlapTimeSlot.setId(mentorSlot.getId());
                overlapTimeSlot.setMentorId(mentor.getId());
                overlapTimeSlot.setMentorName(mentor.getName());
                overlapTimeSlot.setDate(mentorSlot.getDate());
                overlapTimeSlot.setStartTime(mentorSlot.getStartTime());
                overlapTimeSlot.setEndTime(mentorSlot.getEndTime());
                if (mentorSlot.overlaps(menteeSlot)) {
                    overlapTimeSlot.setMatch(true);
                    overlappingSlots.add(overlapTimeSlot);
                } else {
                    overlapTimeSlot.setMatch(false);
                    mentorAvailableSlots.add(overlapTimeSlot);
                }
            }
        }

        if(overlappingSlots.isEmpty()) {
            return (HashSet<OverlapTimeSlot>) mentorAvailableSlots;
        } else {
            return (HashSet<OverlapTimeSlot>) overlappingSlots;
        }
    }

}
