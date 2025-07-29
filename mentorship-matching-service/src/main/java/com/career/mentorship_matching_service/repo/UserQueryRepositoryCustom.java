package com.career.mentorship_matching_service.repo;

import com.career.mentorship_matching_service.model.Mentee;
import com.career.mentorship_matching_service.model.Mentor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQueryRepositoryCustom {
    List<Mentor> findTop3MentorsForMentee(Mentee mentee);
}
