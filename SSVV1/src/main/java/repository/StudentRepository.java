package repository;

import domain.Student;
import validation.*;

public class StudentRepository extends OldAbstractCRUDRepository<String, Student> {
    public StudentRepository(Validator<Student> validator) {
        super(validator);
    }
}

