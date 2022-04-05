package repository;

import domain.*;
import validation.*;

import java.util.HashMap;
import java.util.Map;

public abstract class OldAbstractCRUDRepository<ID, E extends HasID<ID>> implements OldCRUDRepository<ID, E> {
    Map<ID, E> entities;
    Validator<E> validator;

    public OldAbstractCRUDRepository(Validator<E> validator) {
        entities = new HashMap<>();
        this.validator = validator;
    }

    @Override
    public E findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null!\n");
        }

        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() { return entities.values(); }

    @Override
    public E save(E entity) throws ValidationException {
        validator.validate(entity);
        return entities.putIfAbsent(entity.getId(), entity);
    }

    @Override
    public E delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null!\n");
        }

        return entities.remove(id);
    }

    @Override
    public E update(E entity) {
        try {
            validator.validate(entity);
            return entities.replace(entity.getId(), entity);
        } catch (ValidationException exception) {
            System.out.println("Invalid entity!\n");
            return null;
        }
    }
}
