package com.career.mentorship_matching_service.service.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    @Autowired
    Neo4jClient neo4jClient;

    public SchedulerService(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Kolkata")
    public void updateCompletedMeeting() {
        System.out.println("✅ Cron Job Started: updateCompletedMeeting");
        String cQuery = """
                MATCH (m:Meeting)
                WHERE m.status = 'SCHEDULED' AND datetime({date: m.date, time: m.time}) < datetime()
                SET m.status = 'COMPLETED'
                RETURN COUNT(m) AS updatedCount
                """;

        neo4jClient.query(cQuery).run();
    }

}
