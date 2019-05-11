package literarium.parsingData;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public final class XMLUtils {

    public static Document getNewDocInstance() {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        Document doc = null;

        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }

        if (docBuilder != null) {
            doc = docBuilder.newDocument();
        }

        return doc;
    }

    public static Document getDocFromStream(InputStream inStream) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        return docBuilder.parse(inStream);
    }

    public static String docToString(Document doc) throws TransformerException {

        Transformer trans = null;
        StringWriter sw = new StringWriter();

        trans = TransformerFactory.newInstance().newTransformer();

        trans.setOutputProperty(OutputKeys.METHOD, "xml");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        trans.transform(new DOMSource(doc), new StreamResult(sw));

        return sw.toString();
    }

    public static NodeList executeXpath(Document doc, String xPathExpr) throws XPathExpressionException {

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expr = xpath.compile(xPathExpr);

        return (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    }

    public static List<Node> NodeListToListNode(NodeList nl) {

        List<Node> ln = new ArrayList<>();

        for (int i = 0; i < nl.getLength(); ++i) {

            ln.add(nl.item(i));
        }

        return ln;
    }

    static Document getNewDocFromStream(InputStream in) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
