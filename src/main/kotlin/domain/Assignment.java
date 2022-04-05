package domain;

import java.util.Objects;

public class Assignment implements HasID<String> {
    private String id;
    private String description;
    private int deadline;
    private int startWeek;

    public Assignment(String id, String description, int deadline, int startWeek) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
        this.startWeek = startWeek;
    }

    @Override
    public String getId() { return id; }

    @Override
    public void setId(String id) { this.id = id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getDeadline() { return deadline; }

    public void setDeadline(int deadline) { this.deadline = deadline; }

    public int getStartWeek() { return startWeek; }

    public void setStartWeek(int startWeek) { this.startWeek = startWeek; }

    @Override
    public String toString() {
        return "Assignment{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", startWeek=" + startWeek +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Assignment)) return false;

        var assignment = (Assignment) other;
        return id.equals(assignment.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
