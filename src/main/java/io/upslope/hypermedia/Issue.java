package io.upslope.hypermedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

public class Issue {

    private UUID id;
    private String description = "";

    public Issue() {
        this(UUID.randomUUID(), "");
    }

    public Issue(String description) {
        this(UUID.randomUUID(), description);
    }

    public Issue(UUID id, String description) {
        this.id = id;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return getId().equals(issue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
