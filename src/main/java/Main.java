import console.*;
import domain.*;
import repository.*;
import service.*;
import validation.*;

public class Main {
    public static void main(String[] args) {
        Validator<Student> studentValidator = new OldStudentValidator();
        Validator<Assignment> temaValidator = new AssignmentValidator();
        Validator<Grade> notaValidator = new GradeValidator();

        StudentXmlRepository fileRepository1 = new StudentXmlRepository(studentValidator, "studenti.xml");
        AssignmentXmlRepository fileRepository2 = new AssignmentXmlRepository(temaValidator, "teme.xml");
        GradeXmlRepository fileRepository3 = new GradeXmlRepository(notaValidator, "note.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        UI consola = new UI(service);
        consola.run();

        //PENTRU GUI
        // de avut un check: daca profesorul introduce sau nu saptamana la timp
        // daca se introduce nota la timp, se preia saptamana din sistem
        // altfel, se introduce de la tastatura
    }
}
