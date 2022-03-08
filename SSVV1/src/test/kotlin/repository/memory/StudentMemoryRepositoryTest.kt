package repository.memory

import domain.Student
import org.junit.jupiter.api.TestInstance
import repository.StudentRepository
import validation.StudentValidator
import kotlin.test.Test
import kotlin.test.assertFails

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class StudentMemoryRepositoryTest {
    private val studentValidator = StudentValidator()
    private val studentRepository = StudentRepository(studentValidator)

    @Test
    fun saveStudent_emptyId_throwsException() {
        val student = Student("", "hjsgd", 932)

        assertFails {
            studentRepository.save(student)
        }
    }

    @Test
    fun saveStudent_duplicateId_throwsException() {
        val student = Student("jhgh", "hjsgd", 932)

        studentRepository.save(student)
        assertFails {
            studentRepository.save(student)
        }
    }

    @Test
    fun saveStudent_emptyName_throwsException() {
        val student = Student("jhgh", "", 932)

        assertFails {
            studentRepository.save(student)
        }
    }
}