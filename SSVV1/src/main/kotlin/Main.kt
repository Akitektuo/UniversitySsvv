import console.UI
import repository.GradeXmlRepository
import repository.StudentXmlRepository
import repository.AssignmentXmlRepository
import service.Service
import validation.GradeValidator
import validation.StudentValidator
import validation.AssignmentValidator

fun main() {
    val studentValidator = StudentValidator()
    val assignmentValidator = AssignmentValidator()
    val gradeValidator = GradeValidator()

    val studentRepository = StudentXmlRepository(studentValidator, "studenti.xml")
    val assignmentRepository = AssignmentXmlRepository(assignmentValidator, "teme.xml")
    val gradeRepository = GradeXmlRepository(gradeValidator, "note.xml")

    val service = Service(studentRepository, assignmentRepository, gradeRepository)
    val console = UI(service)
    console.run()
}