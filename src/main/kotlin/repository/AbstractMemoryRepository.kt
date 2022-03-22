package repository

import domain.HasID
import validation.ValidationException
import validation.Validator

abstract class AbstractMemoryRepository<ID, E : HasID<ID>>(private val validator: Validator<E>) :
    CrudRepository<ID, E> {
    private val entities = hashMapOf<ID, E>()

    override fun findOne(id: ID): E? {
        id ?: throw IllegalArgumentException("ID cannot be null!\n")

        return entities[id]
    }

    override fun findAll() = entities.values

    override fun save(entity: E) {
        validator.validate(entity)

        entities.putIfAbsent(entity.id, entity)?.let {
            throw ValidationException("Entity already exists!")
        }
    }

    override fun delete(id: ID): E? {
        id ?: throw IllegalArgumentException("ID cannot be null!\n")
        return entities.remove(id)
    }

    override fun update(entity: E): E? {
        return try {
            validator.validate(entity)
            entities.replace(entity.id, entity)!!
        } catch (exception: ValidationException) {
            println("Invalid entity!\n")
            null
        }
    }
}