package repository;

import domain.HasID;
import validation.ValidationException;
import validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public abstract class AbstractXmlRepository<ID, E extends HasID<ID>> extends AbstractCRUDRepository<ID, E> {
    protected String xmlFileName;

    public AbstractXmlRepository(Validator<E> validator, String xmlFileName) {
        super(validator);
        this.xmlFileName = xmlFileName;
    }

    protected abstract E getEntityFromNode(Element node);
    protected abstract Element getElementFromEntity(E entity, Document xmlDocument);

    protected void loadFromXmlFile() {
        try {
            var xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFileName);
            var root = xmlDocument.getDocumentElement();
            var list = root.getChildNodes();

            for(var i = 0; i < list.getLength(); i++) {
                var node = list.item(i);
                if (node.getNodeType() == Element.ELEMENT_NODE) {
                    try {
                        super.save(getEntityFromNode((Element)node));
                    } catch(ValidationException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        } catch(ParserConfigurationException | SAXException | IOException exception) {
            exception.printStackTrace();
        }
    }

    protected void writeToXmlFile() {
        try {
            var xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            var root = xmlDocument.createElement("Entitati");
            xmlDocument.appendChild(root);

            entities.values().forEach(entity -> root.appendChild(getElementFromEntity(entity, xmlDocument)));
            var xmlTransformer = TransformerFactory.newInstance().newTransformer();
            xmlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            xmlTransformer.transform(new DOMSource(xmlDocument), new StreamResult(xmlFileName));
        } catch(ParserConfigurationException | TransformerException exception) {
            exception.printStackTrace();
        }
    }

    protected Element createElement(Document xmlDocument, String tag, String value) {
        var element = xmlDocument.createElement(tag);
        element.setTextContent(value);
        return element;
    }

    @Override
    public E save(E entity) throws ValidationException {
        var result = super.save(entity);
        if (result == null) {
            writeToXmlFile();
        }
        return result;
    }

    @Override
    public E delete(ID id) {
        var result = super.delete(id);
        writeToXmlFile();

        return result;
    }

    @Override
    public E update(E entity) {
        var result = super.update(entity);
        writeToXmlFile();

        return result;
    }
}
