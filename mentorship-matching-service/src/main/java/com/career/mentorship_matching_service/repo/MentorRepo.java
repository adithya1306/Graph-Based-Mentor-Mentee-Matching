package com.career.mentorship_matching_service.repo;

import com.career.mentorship_matching_service.model.Mentor;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface MentorRepo extends Neo4jRepository<Mentor, String> {
    @Query("""
    MATCH (u:Mentor)
    WITH u, toInteger(u.id) AS numericId
    ORDER BY numericId DESC
    RETURN toInteger(u.id)
    LIMIT 1
    """)
    Integer findLastUser();
}
