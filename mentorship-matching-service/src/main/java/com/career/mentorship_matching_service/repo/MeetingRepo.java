package com.career.mentorship_matching_service.repo;

import com.career.mentorship_matching_service.model.Meeting;
import com.career.mentorship_matching_service.model.Mentee;
import com.career.mentorship_matching_service.model.Mentor;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingRepo extends Neo4jRepository<Meeting, Long> {
    Optional<Meeting> findByMenteeAndMentor(Mentee mentee, Mentor mentor);
}
