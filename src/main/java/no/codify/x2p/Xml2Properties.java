package no.codify.x2p;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Xml2Properties {

    public static Either<Exception, Map<?, ?>> from(File xmlFile) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new FileReader(xmlFile));
            Document doc = documentBuilder.parse(inputSource);
            return Either.right(getPropertiesFrom(doc.getDocumentElement(), ""));
        } catch (Exception e) {
            return Either.left(e);
        }
    }

    private static Map<String, String> getPropertiesFrom(Node node, String key) {
        Map<String, String> props = new HashMap<>();

        if ((node.getNodeType() == Node.TEXT_NODE) && !"".equals(node.getNodeValue().trim())) {
            props.put(key, node.getNodeValue().trim());
        } else {
            NodeList childNodes = node.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                String optDot = !key.isEmpty() ? "." : "";
                String newKey = node.getNodeType() == Node.ELEMENT_NODE ? key + optDot + node.getNodeName() : key;
                props.putAll(getPropertiesFrom(childNodes.item(i), newKey));
            }
        }

        return props;
    }
}
