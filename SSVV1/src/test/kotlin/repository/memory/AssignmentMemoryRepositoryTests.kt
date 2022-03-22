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
        val assignment = Assignment("", "some description", 2, 1)

        assertFails {
            assignmentRepository.save(assignment)
        }
    }

    @Test
    fun saveAssignment_emptyDescription_throwsException() {
        val assignment = Assignment("someId", "", 2, 1)

        assertFails {
            assignmentRepository.save(assignment)
        }
    }

    @Test
    fun saveAssignment_deadlineBelowRange_throwsException() {
        val assignment = Assignment("someId", "some description", 0, 1)

        assertFails {
            assignmentRepository.save(assignment)
        }
    }

    @Test
    fun saveAssignment_deadlineAboveRange_throwsException() {
        val assignment = Assignment("someId", "some description", 15, 1)

        assertFails {
            assignmentRepository.save(assignment)
        }
    }

    @Test
    fun saveAssignment_deadlineGraterThanStartWeek_throwsException() {
        val assignment = Assignment("someId", "some description", 8, 10)

        assertFails {
            assignmentRepository.save(assignment)
        }
    }

    @Test
    fun saveAssignment_startWeekBelowRange_throwsException() {
        val assignment = Assignment("someId", "some description", 8, 0)

        assertFails {
            assignmentRepository.save(assignment)
        }
    }

    @Test
    fun saveAssignment_startWeekAboveRange_throwsException() {
        val assignment = Assignment("someId", "some description", 8, 15)

        assertFails {
            assignmentRepository.save(assignment)
        }
    }
}