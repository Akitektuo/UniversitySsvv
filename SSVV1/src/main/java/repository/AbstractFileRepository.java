package repository;

import domain.HasID;
import validation.ValidationException;
import validation.Validator;

public abstract class AbstractFileRepository<ID, E extends HasID<ID>> extends AbstractCRUDRepository<ID,E>{
    protected String fileName;

    public AbstractFileRepository(Validator<E> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
    }

    protected abstract void loadFromFile();
    protected abstract void writeToFile(E entity);
    protected abstract void writeAllToFile();

    @Override
    public Iterable<E> findAll() {
        loadFromFile();
        return super.findAll();
    }

    @Override
    public E save(E entity) throws ValidationException {
        var result = super.save(entity);

        if (result == null) {
            writeToFile(entity);
        }

        return result;
    }

    @Override
    public E delete(ID id) {
        var result = super.delete(id);
        writeAllToFile();

        return result;
    }

    @Override
    public E update(E entity) {
        var result = super.update(entity);
        writeAllToFile();

        return result;
    }
}
