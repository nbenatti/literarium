package com.example.com.literarium;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

public class XmlDataParser {

    private static final String BASE_TAG = "GoodreadsResponse";

    public static Author parseAuthor(InputStream in) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {

        Document doc = XMLUtils.getNewDocFromStream(in);

        String name = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/author/name")).get(0).getTextContent();
        String fansCount = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/author/fans_count")).get(0).getTextContent();
        String imageURL = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/author/image_url")).get(0).getTextContent();
        String smallImageURL = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/author/small_image_url")).get(0).getTextContent();
        String largeImageURL = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/author/large_image_url")).get(0).getTextContent();
        String homeTown = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/author/hometown")).get(0).getTextContent();
        int numBooks = XMLUtils.executeXpath(doc, BASE_TAG+"/author/books/book").getLength();

        List<Book> books = new ArrayList<>();
        Element bookContainerNode = (Element)doc.getElementsByTagName("books").item(0);
        for(int i = 0; i < numBooks; ++i) {

            books.add(parseBook(doc, bookContainerNode));
        }

        return new Author(name, Integer.parseInt(fansCount), imageURL, smallImageURL, largeImageURL, homeTown, numBooks);
    }

    public static Book parseBook(InputStream in) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {

        Document doc = XMLUtils.getNewDocFromStream(in);

        String title = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/title")).get(0).getTextContent();
        String isbn = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/isbn")).get(0).getTextContent();
        String imageURL = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/image_url")).get(0).getTextContent();
        String smallImageURL = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/small_image_url")).get(0).getTextContent();
        String largeImageURL = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/large_image_url")).get(0).getTextContent();
        String publicationYear = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/publication_year")).get(0).getTextContent();
        String publisher = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/publisher")).get(0).getTextContent();
        String description = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/description")).get(0).getTextContent();
        String amazonBuyLink = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/buy_links/buy_link[name='Amazon']/link")).get(0).getTextContent();
        String numPages = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/num_pages")).get(0).getTextContent();
        String author = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/authors/author/name")).get(0).getTextContent();


        return new Book(title,
                isbn,
                imageURL,
                smallImageURL,
                largeImageURL,
                Integer.parseInt(publicationYear),
                publisher,
                description,
                amazonBuyLink,
                Integer.parseInt(numPages),
                author);
    }

    public static Book parseBook(Document doc, Element el) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {

        //Document doc = XMLUtils.getDocFromStream(in);

        String title = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, el.getTagName()+"/book/title")).get(0).getTextContent();
        String isbn = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, el.getTagName()+"/book/isbn")).get(0).getTextContent();
        String imageURL = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, el.getTagName()+"/book/image_url")).get(0).getTextContent();
        String smallImageURL = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, el.getTagName()+"/book/small_image_url")).get(0).getTextContent();
        String largeImageURL = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, el.getTagName()+"/book/large_image_url")).get(0).getTextContent();
        String publicationYear = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, el.getTagName()+"/book/publication_year")).get(0).getTextContent();
        String publisher = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, el.getTagName()+"/book/publisher")).get(0).getTextContent();
        String description = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, el.getTagName()+"/book/description")).get(0).getTextContent();
        String amazonBuyLink = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, el.getTagName()+"/book/buy_links/buy_link[name='Amazon']/link")).get(0).getTextContent();
        String numPages = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, el.getTagName()+"/book/num_pages")).get(0).getTextContent();
        String author = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(doc, BASE_TAG+"/book/authors/author/name")).get(0).getTextContent();

        return new Book(title,
                isbn,
                imageURL,
                smallImageURL,
                largeImageURL,
                Integer.parseInt(publicationYear),
                publisher,
                description,
                amazonBuyLink,
                Integer.parseInt(numPages),
                author);
    }
}
