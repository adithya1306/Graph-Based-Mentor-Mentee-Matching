package com.career.mentorship_matching_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserCommandDTO {
    private String id;
    private String name;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String role;
    private List<String> skills;
    private String industry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public UserCommandDTO() {
    }

    public UserCommandDTO(String id, String name, String email, String role, List<String> skills, String industry) {
        this.id = id;
        this.name = name;
        this.email = email; // Email can be set later if needed
        this.role = role;
        this.skills = skills;
        this.industry = industry;
    }
}
