import console.UI
import repository.NotaXMLRepository
import repository.StudentXMLRepository
import repository.TemaXMLRepository
import service.Service
import validation.NotaValidator
import validation.StudentValidator
import validation.TemaValidator

fun main() {
    val studentValidator = StudentValidator()
    val assignmentValidator = TemaValidator()
    val gradeValidator = NotaValidator()

    val fileRepository1 = StudentXMLRepository(studentValidator, "studenti.xml")
    val fileRepository2 = TemaXMLRepository(assignmentValidator, "teme.xml")
    val fileRepository3 = NotaXMLRepository(gradeValidator, "note.xml")

    val service = Service(fileRepository1, fileRepository2, fileRepository3)
    val console = UI(service)
    console.run()
}