package repository;

import domain.Assignment;
import validation.*;

public class AssignmentRepository extends AbstractCRUDRepository<String, Assignment> {
    public AssignmentRepository(Validator<Assignment> validator) {
        super(validator);
    }
}

