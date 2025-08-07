package com.career.user_service.dto;

public class MailDTO {
    private String mentorMail;
    private String menteeMail;
    private String mentorName;
    private String menteeName;
    private String meetingDate;
    private String meetingStartTime;
    private String meetingEndTime;

    public String getMentorMail() {
        return mentorMail;
    }

    public void setMentorMail(String mentorMail) {
        this.mentorMail = mentorMail;
    }

    public String getMenteeMail() {
        return menteeMail;
    }

    public void setMenteeMail(String menteeMail) {
        this.menteeMail = menteeMail;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMenteeName() {
        return menteeName;
    }

    public void setMenteeName(String menteeName) {
        this.menteeName = menteeName;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(String meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public String getMeetingEndTime() {
        return meetingEndTime;
    }

    public void setMeetingEndTime(String meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }

    public MailDTO() {} // required

}
