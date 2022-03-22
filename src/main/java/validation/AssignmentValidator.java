package validation;
import domain.Assignment;

public class AssignmentValidator implements Validator<Assignment> {
    public void validate(Assignment assignment) throws ValidationException {
        if (assignment.getId() == null || assignment.getId().isEmpty()) {
            throw new ValidationException("Invalid ID!\n");
        }
        if (assignment.getDescription() == null || assignment.getDescription().isEmpty()) {
            throw new ValidationException("Invalid description!\n");
        }
        if (assignment.getDeadline() < 1 || assignment.getDeadline() > 14 || assignment.getDeadline() < assignment.getStartWeek()) {
            throw new ValidationException("Invalid deadline!\n");
        }
        if (assignment.getStartWeek() < 1 || assignment.getStartWeek() > 14 || assignment.getStartWeek() > assignment.getDeadline()) {
            throw new ValidationException("Invalid starting week!\n");
        }
    }
}

