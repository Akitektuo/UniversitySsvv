package validation;
import domain.Grade;

public class GradeValidator implements Validator<Grade> {
    public void validate(Grade grade) throws ValidationException {
        if (grade.getId().getFirst() == null || grade.getId().equals("")) {
            throw new ValidationException("Invalid student ID!\n");
        }
        if (grade.getId().getSecond() == null || grade.getId().equals("")) {
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
