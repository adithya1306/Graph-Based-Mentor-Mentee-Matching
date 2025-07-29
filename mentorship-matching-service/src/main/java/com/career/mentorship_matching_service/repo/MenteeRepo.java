package com.career.mentorship_matching_service.repo;

import com.career.mentorship_matching_service.model.Mentee;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MenteeRepo extends Neo4jRepository<Mentee, String> {
}
