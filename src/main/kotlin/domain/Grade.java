package domain;

public class Grade implements HasID<Pair<String, String>> {
    Pair<String, String> id;
    private double grade;
    private int weekDeadline;
    private String feedback;

    public Grade(Pair<String, String> id, double grade, int weekDeadline, String feedback) {
        this.id = id;
        this.grade = grade;
        this.weekDeadline = weekDeadline;
        this.feedback = feedback;
    }

    @Override
    public Pair<String, String> getId() { return id; }

    @Override
    public void setId(Pair<String, String> id) { this.id = id; }

    public double getGrade() { return grade; }

    public void setGrade(double grade) { this.grade = grade; }

    public int getWeekDeadline() { return weekDeadline; }

    public void setWeekDeadline(int weekDeadline) { this.weekDeadline = weekDeadline; }

    public String getFeedback() { return feedback; }

    public void setFeedback(String feedback) { this.feedback = feedback; }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", grade=" + grade +
                ", weekDeadline=" + weekDeadline +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
