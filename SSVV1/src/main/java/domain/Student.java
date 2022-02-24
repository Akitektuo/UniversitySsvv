package domain;

import java.util.Objects;

public class Student implements HasID<String> {
    private String id;
    private String name;
    private int group;

    public Student(String id, String name, int group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }

    @Override
    public String getID() { return id; }

    @Override
    public void setID(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", group=" + group +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Student)) return false;

        var student = (Student) other;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

