package com.career.mentorship_matching_service.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Mentor")
public class Mentor {
    @Id
    private String id;
    private String name;
    private String email;

    @Relationship(type = "HAS_SKILL", direction = Relationship.Direction.OUTGOING)
    private List<Skill> skills;

    @Relationship(type = "WORKS_IN", direction = Relationship.Direction.OUTGOING)
    private Industry industry;

    @Relationship(type = "AVAILABLE_AT", direction = Relationship.Direction.OUTGOING)
    private List<TimeSlot> availableSlots;

    public Mentor() {
    }

    public Mentor(String id, String name, String email, List<Skill> skills, Industry industry) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.skills = skills;
        this.industry = industry;
    }

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

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public List<TimeSlot> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(List<TimeSlot> availableSlots) {
        this.availableSlots = availableSlots;
    }
}
