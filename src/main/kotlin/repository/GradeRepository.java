package repository;

import domain.*;
import kotlin.Pair;
import validation.*;

public class GradeRepository extends OldAbstractCRUDRepository<Pair<String, String>, Grade> {
    public GradeRepository(Validator<Grade> validator) {
        super(validator);
    }
}
