package validation;
import domain.Student;

public class OldStudentValidator implements Validator<Student> {
    public void validate(Student student) throws ValidationException {
        if (student.getId() == null || student.getId().isEmpty()) {
            throw new ValidationException("Invalid ID!\n");
        }
        if (student.getName() == null || student.getName().isEmpty()) {
            throw new ValidationException("Invalid name!\n");
        }
        if (student.getGroup() <= 110 || student.getGroup() >= 938) {
            throw new ValidationException("Invalid group!\n");
        }
    }
}

