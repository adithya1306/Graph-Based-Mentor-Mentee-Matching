package com.career.mentorship_matching_service.repo;

import com.career.mentorship_matching_service.model.Mentee;
import com.career.mentorship_matching_service.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface MenteeRepo extends Neo4jRepository<Mentee, String> {
    @Query("""
    MATCH (u:Mentee)
    WITH u, toInteger(u.id) AS numericId
    ORDER BY numericId DESC
    RETURN toInteger(u.id)
    LIMIT 1
    """)
    Integer findLastUser();

    @Query("""
    MATCH (m:Mentee {id: $id}) - [r:MENTORED_BY] -> (mentor:Mentor)
    DELETE r
    """)
    void deleteMentorRelationship(String id);

}
