package com.example.com.parsingData;

import android.util.Log;

import com.example.com.parsingData.parseType.Author;
import com.example.com.parsingData.parseType.Book;
import com.example.com.parsingData.parseType.Shelf;
import com.example.com.parsingData.parseType.User;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

public final class XmlDataParser {

    private static final String BASE_TAG = "GoodreadsResponse";

    private static final String ERROR_TAG = "error";



    private static String getStringValueFromPath(Document doc, String path) throws XPathExpressionException {
        NodeList nodeList = ParseUtils.executeXpath(doc, BASE_TAG + path);
        if(!nodeList.item(0).getTextContent().isEmpty())
            Log.d("__XMLDataParser", nodeList.item(0).getTextContent());
        else
            Log.d("__XMLDataParser", path + " is empty ");
        List<Node> listNode = ParseUtils.NodeListToListNode(nodeList);
        return listNode.get(0).getTextContent();
    }

    public static Author parseAuthor(Document doc) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, InterruptedException {

        String id = getStringValueFromPath(doc, "/author/id");
        String name = getStringValueFromPath(doc, "/author/name");
        String fans_count = getStringValueFromPath(doc, "/author/fans_count");
        String image_url = getStringValueFromPath(doc, "/author/image_url");
        String about = getStringValueFromPath(doc, "/author/about");
        String works_count = getStringValueFromPath(doc, "/author/works_count");
        String gender = getStringValueFromPath(doc, "/author/gender");
        String homeTown = getStringValueFromPath(doc, "/author/hometown");
        String born_at = getStringValueFromPath(doc, "/author/born_at");
        String died_at = getStringValueFromPath(doc, "/author/died_at");

        List<Book> books = Collections.synchronizedList(new ArrayList<Book>());
        NodeList bookList = ParseUtils.executeXpath(doc, BASE_TAG + "/author/books/book/id");

        BookThread[] threads = new BookThread[bookList.getLength()];

        for (int i = 0; i < bookList.getLength(); ++i) {
            String bookId = bookList.item(i).getTextContent();
            threads[i] = new BookThread(bookId);
            threads[i].start();
        }

        for (int i = 0; i < bookList.getLength(); ++i) {
            threads[i].join();
            books.add(threads[i].getBook());
        }

        return new com.example.com.parsingData.parseType.Author(Integer.valueOf(id), name, Integer.valueOf(fans_count), image_url, about, Integer.valueOf(works_count), gender, homeTown, born_at, died_at, (Book[]) books.toArray());
    }

    public static com.example.com.parsingData.parseType.Book parseBook(Document doc) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        //Document doc = ParseUtils.getNewDocFromStream(in);

        String id = getStringValueFromPath(doc, "/book/id");
        String title = getStringValueFromPath(doc, "/book/title");
        String isbn = getStringValueFromPath(doc, "/book/isbn");
        String image_url = getStringValueFromPath(doc, "/book/image_url");
        String publication_year = getStringValueFromPath(doc, "/book/publication_year");
        String publisher = getStringValueFromPath(doc, "/book/publisher");
        String description = getStringValueFromPath(doc, "/book/description");
        String average_rating = getStringValueFromPath(doc, "/book/average_rating");
        String num_pages = getStringValueFromPath(doc, "/book/num_pages");

        String authorsId = getStringValueFromPath(doc, "/book/authors/author/id");
        String authorsName = getStringValueFromPath(doc, "/book/authors/author/name");
        com.example.com.parsingData.parseType.AuthorInfo author = new com.example.com.parsingData.parseType.AuthorInfo(Integer.valueOf(authorsId), authorsName);

        return new com.example.com.parsingData.parseType.Book(Integer.valueOf(id), title, isbn, image_url, publication_year, publisher, description, Double.valueOf(average_rating), num_pages, author);
    }

    public static List<Book> parseSearch(Document doc) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException, InterruptedException {
        //Document doc = ParseUtils.getNewDocFromStream(in);

        List<Book> books = Collections.synchronizedList(new ArrayList<Book>());
        NodeList bookList = ParseUtils.executeXpath(doc, BASE_TAG + "/search/results/work/best_book/id");

        BookThread[] threads = new BookThread[bookList.getLength()];

        for (int i = 0; i < bookList.getLength(); ++i) {
            String bookId = bookList.item(i).getTextContent();
            threads[i] = new BookThread(bookId);
            threads[i].start();
        }

        for (int i = 0; i < bookList.getLength(); ++i) {
            threads[i].join();
            books.add(threads[i].getBook());
        }

        return books;
    }

    public static User parseUserInfo(Document doc) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        //Document doc = ParseUtils.getNewDocFromStream(in);

        //Element rootNode = doc.getDocumentElement();

        /*if(rootNode.getTagName().equals(ERROR_TAG) && rootNode.getTextContent().equals("profile not found")) {

            return null;
        }*/

        String name = getStringValueFromPath(doc, "/user/name");
        String username = getStringValueFromPath(doc, "/user/username");
        String image_url = getStringValueFromPath(doc, "/user/image_url");
        String about = getStringValueFromPath(doc, "/user/about");
        String age = getStringValueFromPath(doc, "/user/age");
        String gender = getStringValueFromPath(doc, "/user/gender");
        String interests = getStringValueFromPath(doc, "/user/interests");
        String friends_count = getStringValueFromPath(doc, "/user/friends_count");
        String reviews_count = getStringValueFromPath(doc, "/user/reviews_count");

        List<com.example.com.parsingData.parseType.Shelf> shelves = new ArrayList<>();
        NodeList shelvesList = ParseUtils.executeXpath(doc, BASE_TAG + "/user/user_shelves/user_shelf");
        for (int i = 0; i < shelvesList.getLength(); ++i) {
            Element node = (Element) shelvesList.item(i);
            String shelfName = node.getElementsByTagName("name").item(0).getTextContent();
            String shelfBook_count = node.getElementsByTagName("book_count").item(0).getTextContent();
            Shelf shelf = new Shelf(shelfName, Integer.valueOf(shelfBook_count));
            shelves.add(shelf);
        }

        return new User(name, username, image_url, about, Integer.valueOf(age), gender, interests, Integer.valueOf(friends_count), Integer.valueOf(reviews_count), (Shelf[]) shelves.toArray());
    }
}
