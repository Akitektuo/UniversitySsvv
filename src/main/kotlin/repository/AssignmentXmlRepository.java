package repository;

import domain.Assignment;
import validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AssignmentXmlRepository extends AbstractXmlRepository<String, Assignment> {

    public AssignmentXmlRepository(Validator<Assignment> validator, String xmlFileName) {
        super(validator, xmlFileName);
        loadFromXmlFile();
    }

    protected Element getElementFromEntity(Assignment assignment, Document xmlDocument) {
        Element element = xmlDocument.createElement("tema");
        element.setAttribute("ID", assignment.getId());

        element.appendChild(createElement(xmlDocument, "Descriere", assignment.getDescription()));
        element.appendChild(createElement(xmlDocument, "Deadline", String.valueOf(assignment.getDeadline())));
        element.appendChild(createElement(xmlDocument, "Startline", String.valueOf(assignment.getStartWeek())));

        return element;
    }

    protected Assignment getEntityFromNode(Element node) {
        String id = node.getAttributeNode("ID").getValue();
        String description = node.getElementsByTagName("Descriere").item(0).getTextContent();
        int deadline = Integer.parseInt(node.getElementsByTagName("Deadline").item(0).getTextContent());
        int weekStart = Integer.parseInt(node.getElementsByTagName("Startline").item(0).getTextContent());

        return new Assignment(id, description, deadline, weekStart);
    }
}
