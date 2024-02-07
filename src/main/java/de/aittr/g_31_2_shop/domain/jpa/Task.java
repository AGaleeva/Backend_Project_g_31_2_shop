package de.aittr.g_31_2_shop.domain.jpa;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "executed_at")
    private Timestamp executedAt;

    public Task() {
        executedAt = new Timestamp(System.currentTimeMillis());
    }

    public Task(String description) {
        this.description = description;
        executedAt = new Timestamp(System.currentTimeMillis());
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id == task.id && Objects.equals(description, task.description) && Objects.equals(executedAt, task.executedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, executedAt);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Task{");
        sb.append("id=").append(id);
        sb.append(", description='").append(description).append('\'');
        sb.append(", executedAt=").append(executedAt);
        sb.append('}');
        return sb.toString();
    }
}
