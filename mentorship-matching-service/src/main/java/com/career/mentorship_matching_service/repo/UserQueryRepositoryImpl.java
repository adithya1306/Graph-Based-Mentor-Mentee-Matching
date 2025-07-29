package com.career.mentorship_matching_service.repo;

import com.career.mentorship_matching_service.model.Mentee;
import com.career.mentorship_matching_service.model.Mentor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class UserQueryRepositoryImpl implements UserQueryRepositoryCustom{
    @Autowired
    Neo4jClient neo4jClient;

    @Autowired
    private MenteeRepo menteeRepo;

    @Autowired
    private MentorRepo mentorRepo;

    @Override
    public List<Mentor> findTop3MentorsForMentee(Mentee mentee) {
        return neo4jClient.query("""
    MATCH (mentee:Mentee {id: $menteeId})-[:HAS_SKILL]->(ms:Skill),
          (mentee)-[:WORKS_IN]->(i:Industry),
          (mentor:Mentor)-[:HAS_SKILL]->(ms),
          (mentor)-[:WORKS_IN]->(i)
    WHERE mentee <> mentor
    WITH mentor, count(ms) AS sharedSkillCount
    ORDER BY sharedSkillCount DESC
    LIMIT 3
    RETURN mentor
    """)
                .bind(mentee.getId()).to("menteeId")
                .fetchAs(Mentor.class)
                .mappedBy((typeSystem, record) -> {
                    return mentorRepo.findById(record.get("mentor").asNode().get("id").asString()).orElse(null);
                })
                .all()
                .stream()
                .filter(Objects::nonNull)
                .toList();
    }
}
