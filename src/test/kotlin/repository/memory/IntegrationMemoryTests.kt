package repository.memory

import domain.Assignment
import domain.Grade
import domain.Student
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import repository.AssignmentRepository
import repository.GradeRepository
import repository.StudentMemoryRepository
import validation.AssignmentValidator
import validation.GradeValidator
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class IntegrationMemoryTests {
    private val studentRepository = StudentMemoryRepository()
    private val assignmentValidator = AssignmentValidator()
    private val assignmentRepository = AssignmentRepository(assignmentValidator)
    private val gradeValidator = GradeValidator()
    private val gradeRepository = GradeRepository(gradeValidator)

    @Test
    fun saveStudent_fieldsValid_studentAdded() {
        val student = Student("jhgh", "hjsgd", 932)

        studentRepository.save(student)
        val savedStudent = studentRepository.findAll().last()

        assertEquals(student, savedStudent, "The same student was expected")
    }

    @Test
    fun saveAssignment_validData_savesSameAssignment() {
        val assignment = Assignment("someId", "some description", 10, 8)

        assignmentRepository.save(assignment)
        val savedAssignment = assignmentRepository.findAll().last()

        assertEquals(assignment, savedAssignment, "The same assignment was expected")
    }

    @Test
    fun saveGrade_validData_savesSameGrade() {
        val grade = Grade("someStudentId" to "someAssignmentId", 10.0, 8, "")

        gradeRepository.save(grade)
        val savedGrade = gradeRepository.findAll().last()

        assertEquals(grade, savedGrade, "The same grade was expected")
    }

    @Test
    fun saveStudentAssignmentGrade_validData_savesSameStudentAssignmentGrade() {
        val student = Student("jhgh", "hjsgd", 932)
        val assignment = Assignment("someId", "some description", 10, 8)
        val grade = Grade(student.id to assignment.id, 10.0, 8, "")

        studentRepository.save(student)
        assignmentRepository.save(assignment)
        gradeRepository.save(grade)
        val savedStudent = studentRepository.findAll().last()
        val savedAssignment = assignmentRepository.findAll().last()
        val savedGrade = gradeRepository.findAll().last()

        assertEquals(student, savedStudent, "The same student was expected")
        assertEquals(assignment, savedAssignment, "The same assignment was expected")
        assertEquals(grade, savedGrade, "The same grade was expected")
    }
}