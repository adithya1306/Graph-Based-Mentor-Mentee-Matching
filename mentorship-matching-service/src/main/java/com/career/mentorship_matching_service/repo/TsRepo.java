package com.career.mentorship_matching_service.repo;

import com.career.mentorship_matching_service.model.TimeSlot;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TsRepo extends Neo4jRepository<TimeSlot, Long> {
    Optional<TimeSlot> findByDateAndStartTimeAndEndTime(String date, String startTime, String endTime);
}
