package com.career.mentorship_matching_service.repo;

import com.career.mentorship_matching_service.dto.RescheduleMeetingDTO;
import org.neo4j.driver.summary.ResultSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

@Component
public class MeetingQueryImpl implements MeetingQuery{

    @Autowired
    Neo4jClient neo4jClient;

    @Override
    public ResultSummary rescheduleMeeting(RescheduleMeetingDTO rescheduleMeeting, String mentorId) {
        String cQuery = """
                    MATCH (m:Meeting) - [:SCHEDULED_BY] -> (:Mentee {id: $menteeId})
                    MATCH (m) - [:HOSTING] -> (:Mentor {id: $mentorId})
                    SET m.date = $date, m.startTime = $startTime, m.endTime = $endTime, m.status = 'SCHEDULED'
                    RETURN m
                """;

        return neo4jClient.query(cQuery)
                .bind(rescheduleMeeting.getMenteeId()).to("menteeId")
                .bind(mentorId).to("mentorId")
                .bind(rescheduleMeeting.getDate()).to("date")
                .bind(rescheduleMeeting.getStartTime()).to("startTime")
                .bind(rescheduleMeeting.getEndTime()).to("endTime")
                .run();
    }

    @Override
    public ResultSummary cancelMeeting(RescheduleMeetingDTO cancelMeeting, String mentorId) {
        String cQuery = """
                    MATCH (m:Meeting) - [:SCHEDULED_BY] -> (:Mentee {id: $menteeId})
                    MATCH (m) - [:HOSTING] -> (:Mentor {id: $mentorId})
                    WHERE m.status <> 'CANCELLED'
                    SET m.status = 'CANCELLED'
                    RETURN m
                """;

        return neo4jClient.query(cQuery)
                .bind(cancelMeeting.getMenteeId()).to("menteeId")
                .bind(mentorId).to("mentorId")
                .run();
    }
}
