package repository;

import domain.Grade;
import domain.Student;
import kotlin.Pair;
import validation.OldStudentValidator;
import validation.AssignmentValidator;
import validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GradeXmlRepository extends AbstractXmlRepository<Pair<String, String>, Grade> {

    public GradeXmlRepository(Validator<Grade> validator, String xmlFileName) {
        super(validator, xmlFileName);
        loadFromXmlFile();
    }

    protected Element getElementFromEntity(Grade grade, Document xmlDocument) {
        Element element = xmlDocument.createElement("nota");
        element.setAttribute("IDStudent", grade.getId().getFirst());
        element.setAttribute("IDTema", grade.getId().getSecond());

        element.appendChild(createElement(xmlDocument, "Nota", String.valueOf(grade.getGrade())));
        element.appendChild(createElement(xmlDocument, "SaptamanaPredare", String.valueOf(grade.getWeekDeadline())));
        element.appendChild(createElement(xmlDocument, "Feedback", grade.getFeedback()));

        return element;
    }

    protected Grade getEntityFromNode(Element node) {
        String studentId = node.getAttributeNode("IDStudent").getValue();
        String assignmentId= node.getAttributeNode("IDTema").getValue();
        double grade = Double.parseDouble(node.getElementsByTagName("Nota").item(0).getTextContent());
        int deadline = Integer.parseInt(node.getElementsByTagName("SaptamanaPredare").item(0).getTextContent());
        String feedback = node.getElementsByTagName("Feedback").item(0).getTextContent();

        return new Grade(new Pair<>(studentId, assignmentId), grade, deadline, feedback);
    }

    public void createFile(Grade grade) {
        String studentId = grade.getId().getFirst();
        OldStudentValidator studentValidator = new OldStudentValidator();
        AssignmentValidator assignmentValidator = new AssignmentValidator();
        StudentFileRepository studentRepository = new StudentFileRepository(studentValidator, "studenti.txt");
        AssignmentFileRepository assignmentRepository = new AssignmentFileRepository(assignmentValidator, "teme.txt");

        Student student = studentRepository.findOne(studentId);
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(student.getName() + ".txt", false))) {
            findAll().forEach(gradeElement -> {
                if (!gradeElement.getId().getFirst().equals(studentId)) {
                    return;
                }
                try {
                    writter.write("Tema: " + gradeElement.getId().getSecond() + "\n");
                    writter.write("Nota: " + gradeElement.getGrade() + "\n");
                    writter.write("Predata in saptamana: " + gradeElement.getWeekDeadline() + "\n");
                    writter.write("Deadline: " + assignmentRepository.findOne(gradeElement.getId().getSecond()).getDeadline() + "\n");
                    writter.write("Feedback: " + gradeElement.getFeedback() + "\n\n");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
//    public void createFile(Nota notaObj) {
//        String idStudent = notaObj.getId().getObject1();
//        StudentValidator sval = new StudentValidator();
//        TemaValidator tval = new TemaValidator();
//        StudentXMLRepository srepo = new StudentXMLRepository(sval, "studenti.xml");
//        TemaXMLRepository trepo = new TemaXMLRepository(tval, "teme.xml");
//
//        Student student = srepo.findOne(idStudent);
//        try {
//            Document XMLdocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//            Element root = XMLdocument.createElement("NoteStudent");
//            XMLdocument.appendChild(root);
//
//            super.findAll().forEach(nota -> {
//                if (nota.getId().getObject1().equals(idStudent)) {
//                    try {
//                        Document XMLstudent = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//                        Element element = XMLstudent.createElement("nota");
//
//                        Document XMLdocument2 = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(trepo.XMLfilename);
//                        Node n = XMLdocument2.getFirstChild();
//                        Node temaNode = XMLstudent.importNode(XMLdocument2, true);
//                        Tema t = trepo.getEntityFromNode((Element) temaNode);
//
//                        element.appendChild(createElement(XMLstudent, "Tema", t.getId()));
//                        element.appendChild(createElement(XMLstudent, "Nota", String.valueOf(nota.getNota())));
//                        element.appendChild(createElement(XMLstudent, "SaptamanaPredare", String.valueOf(nota.getSaptamanaPredare())));
//                        element.appendChild(createElement(XMLstudent, "Deadline", String.valueOf(t.getDeadline())));
//                        element.appendChild(createElement(XMLstudent, "Feedback", nota.getFeedback()));
//
//                        root.appendChild(element);
//
//                    } catch (ParserConfigurationException e) {
//                        e.printStackTrace();
//                    } catch (SAXException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//
//            Transformer XMLtransformer = TransformerFactory.newInstance().newTransformer();
//            XMLtransformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            XMLtransformer.transform(new DOMSource(XMLdocument), new StreamResult(XMLfilename));
//        }
//        catch(ParserConfigurationException pce) {
//            pce.printStackTrace();
//        }
//        catch(TransformerConfigurationException tce) {
//            tce.printStackTrace();
//        }
//        catch(TransformerException te) {
//            te.printStackTrace();
//        }
//    }}
