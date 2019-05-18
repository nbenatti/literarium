package com.example.com.parsingData;

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

public class ParseUtils {

    private static DocumentBuilder getNewDocBuilder() {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        Document doc = null;

        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }

        return docBuilder;
    }

    /**
     * simply builds an instance of a Document.
     * @return a brand new Document.
     */
    public static Document getNewDocInstance() {

        DocumentBuilder docBuilder = getNewDocBuilder();

        Document doc = docBuilder.newDocument();

        return doc;
    }

    public static Document getNewDocFromStream(InputStream in) throws SAXException, IOException {

        DocumentBuilder docBuilder = getNewDocBuilder();

        Document doc = docBuilder.parse(in);

        return doc;
    }

    public static Document getNewDocFromString(String in) throws SAXException, IOException {

        DocumentBuilder docBuilder = getNewDocBuilder();

        Document doc = docBuilder.parse(in);

        return doc;
    }

    /**
     * extracts the content of a Document into a string.
     * @param doc the document
     * @return a string containing the doc's content
     * @throws TransformerException
     */
    public static String docToString(Document doc) throws TransformerException {

        Transformer trans = null;
        // capture the content of a stream into a string.
        StringWriter sw = new StringWriter();

        //try {
        trans = TransformerFactory.newInstance().newTransformer();
        // configure the output
        trans.setOutputProperty(OutputKeys.METHOD, "xml");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        trans.transform(new DOMSource(doc), new StreamResult(sw));
        /*} catch (TransformerException ex) {
            Logger.getLogger(NearbyServer.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        return sw.toString();
    }

    /**
     * executes an Xpath expression
     * @param doc the document
     * @param xPathExpr string containing the xPath expression
     * @return list of nodes containing the result of the expression.
     * @throws XPathExpressionException when the expression is invalid
     */
    public static NodeList executeXpath(Document doc, String xPathExpr) throws XPathExpressionException {

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expr = xpath.compile(xPathExpr);

        NodeList res = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);

        if(res.getLength() == 0)
            return null;
        else
            return res;
    }

    /**
     * converts a NodeList into a List<Node>
     * @param nl the NodeList
     * @return the List<Node> with the same content of <b>nl</b>.
     */
    public static List<Node> NodeListToListNode(NodeList nl) {

        List<Node> ln = new ArrayList<>();

        if(nl.getLength() == 0)
            return new ArrayList<>();

        for(int i = 0; i < nl.getLength(); ++i) {

            ln.add(nl.item(i));
        }

        return ln;
    }
}