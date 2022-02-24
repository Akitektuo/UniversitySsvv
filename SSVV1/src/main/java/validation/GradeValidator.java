package validation;
import domain.Grade;

public class GradeValidator implements Validator<Grade> {
    public void validate(Grade grade) throws ValidationException {
        if (grade.getID().getFirst() == null || grade.getID().equals("")) {
            throw new ValidationException("Invalid student ID!\n");
        }
        if (grade.getID().getSecond() == null || grade.getID().equals("")) {
            throw new ValidationException("Invalid assignment ID!\n");
        }
        if (grade.getGrade() < 0 || grade.getGrade() > 10) {
            throw new ValidationException("Invalid grade!\n");
        }
        if (grade.getWeekDeadline() < 0) {
            throw new ValidationException("Invalid deadline!\n");
        }
    }
}
