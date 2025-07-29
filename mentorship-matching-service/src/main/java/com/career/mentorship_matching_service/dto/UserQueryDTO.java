package com.career.mentorship_matching_service.dto;

import com.career.mentorship_matching_service.model.Skill;
import lombok.Data;

import java.util.List;

@Data
public class UserQueryDTO {
    private String id;
    private String name;
    private String email;
    private List<Skill> skills;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public UserQueryDTO() {
    }

    public UserQueryDTO(String id, String name, String email, List<Skill> skills, String industry) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.skills = skills;
        this.industry = industry;
    }
}
