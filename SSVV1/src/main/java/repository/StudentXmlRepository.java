package repository;
import domain.Student;
import validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class StudentXmlRepository extends AbstractXmlRepository<String, Student> {

    public StudentXmlRepository(Validator<Student> validator, String xmlFileName) {
        super(validator, xmlFileName);
        loadFromXmlFile();
    }

    protected Element getElementFromEntity(Student student, Document xmlDocument) {
        var element = xmlDocument.createElement("student");
        element.setAttribute("ID", student.getId());

        element.appendChild(createElement(xmlDocument, "Nume", student.getName()));
        element.appendChild(createElement(xmlDocument, "Grupa", String.valueOf(student.getGroup())));

        return element;
    }

    protected Student getEntityFromNode(Element node) {
        var id = node.getAttributeNode("ID").getValue();
        var name = node.getElementsByTagName("Nume").item(0).getTextContent();
        var group = Integer.parseInt(node.getElementsByTagName("Grupa").item(0).getTextContent());

        return new Student(id, name, group);
    }
}
