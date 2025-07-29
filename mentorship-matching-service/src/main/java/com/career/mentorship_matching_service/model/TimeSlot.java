package com.career.mentorship_matching_service.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("TimeSlot")
public class TimeSlot {
    @Id
    @GeneratedValue
    private Long id;

    private String date;
    private String startTime;
    private String endTime;

    public TimeSlot() {
    }

    public TimeSlot(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

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

    public Integer hoursToSecs(String Time) {
        String parts[] = Time.split(":");
        Integer secs = Integer.parseInt(parts[0]) * 3600
                + Integer.parseInt(parts[1]) * 60
                + Integer.parseInt(parts[2]);
        return secs;
    }

    public boolean overlaps(TimeSlot other) {
        return this.date.equals(other.date)
                && hoursToSecs(this.startTime) <= hoursToSecs(other.endTime)
                && hoursToSecs(this.endTime) >= hoursToSecs(other.startTime);
    }
}
