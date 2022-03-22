package repository.memory

import domain.Assignment
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import repository.AssignmentRepository
import validation.AssignmentValidator
import kotlin.test.assertFails

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class AssignmentMemoryRepositoryTests {
    private val assignmentValidator = AssignmentValidator()
    private val assignmentRepository = AssignmentRepository(assignmentValidator)

    @Test
    fun saveAssignment_emptyId_throwsException() {
        val assignment = Assignment("", "", 2, 1)

        assertFails {
            assignmentRepository.save(assignment)
        }
    }
}