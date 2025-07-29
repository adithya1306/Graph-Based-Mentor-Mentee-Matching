package com.career.mentorship_matching_service.dto;

public class MeetingRequestDTO {
    private String mentorId;
    private String date;
    private String startTime;
    private String endTime;

    public String getMentorId() {
        return mentorId;
    }

    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
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

    public MeetingRequestDTO() {
    }

    public MeetingRequestDTO(String mentorId, String date, String startTime, String endTime) {
        this.mentorId = mentorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
