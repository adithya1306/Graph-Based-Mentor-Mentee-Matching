package com.career.mentorship_matching_service.repo;

import com.career.mentorship_matching_service.dto.RescheduleMeetingDTO;
import org.neo4j.driver.summary.ResultSummary;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingQuery {
    ResultSummary rescheduleMeeting(RescheduleMeetingDTO rescheduleMeetingDTO, String mentorId);
    ResultSummary cancelMeeting(RescheduleMeetingDTO cancelMeeting, String mentorId);
}
