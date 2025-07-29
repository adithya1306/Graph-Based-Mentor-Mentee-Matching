package com.career.mentorship_matching_service.repo;

import com.career.mentorship_matching_service.model.Mentor;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MentorRepo extends Neo4jRepository<Mentor, String> {
}
