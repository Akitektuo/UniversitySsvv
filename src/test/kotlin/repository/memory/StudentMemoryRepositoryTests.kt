package repository.memory

import domain.Student
import org.junit.jupiter.api.TestInstance
import repository.StudentMemoryRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class StudentMemoryRepositoryTests {
    private val studentRepository = StudentMemoryRepository()

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

    @Test
    fun saveStudent_groupNotValid_throwsException() {
        val student = Student("jhgh", "hjsgd", 148)

        assertFails {
            studentRepository.save(student)
        }
    }

    @Test
    fun saveStudent_fieldsValid_studentAdded() {
        val student = Student("jhgh", "hjsgd", 932)

        studentRepository.save(student)
        val savedStudent = studentRepository.findAll().last()

        assertEquals(student, savedStudent, "The same student was expected")
    }
}