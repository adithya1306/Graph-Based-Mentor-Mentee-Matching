package com.career.mentorship_matching_service.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class Meeting {
    @Id
    @GeneratedValue
    private Long id;

    private String date;
    private String startTime;
    private String endTime;
    private String status; // SCHEDULED, COMPLETED, CANCELLED


    @Relationship(type = "HOSTING", direction = Relationship.Direction.OUTGOING)
    private Mentor mentor;

    @Relationship(type = "SCHEDULED_BY", direction = Relationship.Direction.OUTGOING)
    private Mentee mentee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public Mentee getMentee() {
        return mentee;
    }

    public void setMentee(Mentee mentee) {
        this.mentee = mentee;
    }

    public Meeting() {
    }

    public Meeting(Long id, String date, String startTime, String endTime, String status, Mentor mentor, Mentee mentee) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.mentor = mentor;
        this.mentee = mentee;
    }
}
