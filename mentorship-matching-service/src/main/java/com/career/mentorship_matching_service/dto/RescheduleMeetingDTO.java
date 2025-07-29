package com.career.mentorship_matching_service.dto;

public class RescheduleMeetingDTO {
    private String menteeId;
    private String date;
    private String startTime;
    private String endTime;

    public String getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(String menteeId) {
        this.menteeId = menteeId;
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

    public RescheduleMeetingDTO() {
    }

    public RescheduleMeetingDTO(String menteeId, String date, String startTime, String endTime) {
        this.menteeId = menteeId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
