package com.career.mentorship_matching_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Industry")
public class Industry {
    @Id
    private String name;

    public Industry(String name) {
        this.name = name;
    }

    public Industry() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
