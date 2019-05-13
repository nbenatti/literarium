package literarium.parsingData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import literarium.parsingData.enumType.RequestType;
import literarium.parsingData.parseType.Author;
import literarium.parsingData.parseType.AuthorInfo;
import literarium.parsingData.parseType.Book;
import literarium.parsingData.parseType.Shelf;
import literarium.parsingData.parseType.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class XmlDataParser {

    private static final String BASE_TAG = "GoodreadsResponse";

    private static String getStringValueFromPath(Document doc, String path) throws XPathExpressionException {
        NodeList nodeList = XMLUtils.executeXpath(doc, BASE_TAG + path);
        List<Node> listNode = XMLUtils.NodeListToListNode(nodeList);
        return listNode.get(0).getTextContent();
    }

    public static Author parseAuthor(InputStream in) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        Document doc = XMLUtils.getDocFromStream(in);

        String id = getStringValueFromPath(doc, "/author/id");
        String name = getStringValueFromPath(doc, "/author/name");
        String fans_count = getStringValueFromPath(doc, "/author/fans_count");
        String image_url = getStringValueFromPath(doc, "/author/image_url");
        String about = getStringValueFromPath(doc, "/author/about");
        String works_count = getStringValueFromPath(doc, "/author/works_count");
        String gender = getStringValueFromPath(doc, "/author/gender");
        String homeTown = getStringValueFromPath(doc, "/author/homeTown");
        String born_at = getStringValueFromPath(doc, "/author/born_at");
        String died_at = getStringValueFromPath(doc, "/author/died_at");

        List<Book> books = new ArrayList<>();
        NodeList bookList = XMLUtils.executeXpath(doc, BASE_TAG + "/author/books/book/id");
        for (int i = 0; i < bookList.getLength(); ++i) {
            String bookId = bookList.item(i).getTextContent();
            String url = URLRequestFormatter.format(RequestType.BOOK_SHOW, bookId);
            Book book = parseBook(new URL(url).openStream());
            books.add(book);
        }

        return new Author(Integer.valueOf(id), name, Integer.valueOf(fans_count), image_url, about, Integer.valueOf(works_count), gender, homeTown, born_at, died_at, (Book[]) books.toArray());
    }

    public static Book parseBook(InputStream in) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        Document doc = XMLUtils.getDocFromStream(in);

        String id = getStringValueFromPath(doc, "/book/id");
        String title = getStringValueFromPath(doc, "/book/title");
        String isbn = getStringValueFromPath(doc, "/book/isbn");
        String image_url = getStringValueFromPath(doc, "/book/image_url");
        String publication_year = getStringValueFromPath(doc, "/book/publication_year");
        String publisher = getStringValueFromPath(doc, "/book/publisher");
        String description = getStringValueFromPath(doc, "/book/description");
        String amazon_buy_link = getStringValueFromPath(doc, "/book/buy_links/buy_link/link");
        String average_rating = getStringValueFromPath(doc, "/book/average_rating");
        String num_pages = getStringValueFromPath(doc, "/book/num_pages");

        List<AuthorInfo> authors = new ArrayList<>();
        NodeList authorsList = XMLUtils.executeXpath(doc, BASE_TAG + "/book/authors/author");
        for (int i = 0; i < authorsList.getLength(); ++i) {
            Element node = (Element) authorsList.item(i);
            String authorsId = node.getElementsByTagName("id").item(0).getTextContent();
            String authorsName = node.getElementsByTagName("name").item(0).getTextContent();
            AuthorInfo author = new AuthorInfo(Integer.valueOf(authorsId), authorsName);
            authors.add(author);
        }

        return new Book(Integer.valueOf(id), title, isbn, image_url, Integer.valueOf(publication_year), publisher, description, amazon_buy_link, Double.valueOf(average_rating), Integer.valueOf(num_pages), (AuthorInfo[]) authors.toArray());
    }

    public static Book[] parseSearch(InputStream in) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
        Document doc = XMLUtils.getDocFromStream(in);

        List<Book> books = new ArrayList<>();
        NodeList bookList = XMLUtils.executeXpath(doc, BASE_TAG + "/search/results/work/id");
        for (int i = 0; i < bookList.getLength(); ++i) {
            String bookId = bookList.item(i).getTextContent();
            String url = URLRequestFormatter.format(RequestType.BOOK_SHOW, bookId);
            Book book = parseBook(new URL(url).openStream());
            books.add(book);
        }

        return (Book[]) books.toArray();
    }

    public static User parseUserInfo(InputStream in) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        Document doc = XMLUtils.getDocFromStream(in);

        String name = getStringValueFromPath(doc, "/user/name");
        String username = getStringValueFromPath(doc, "/user/username");
        String image_url = getStringValueFromPath(doc, "/user/image_url");
        String about = getStringValueFromPath(doc, "/user/about");
        String age = getStringValueFromPath(doc, "/user/age");
        String gender = getStringValueFromPath(doc, "/user/gender");
        String interests = getStringValueFromPath(doc, "/user/interests");
        String friends_count = getStringValueFromPath(doc, "/user/friends_count");
        String reviews_count = getStringValueFromPath(doc, "/user/reviews_count");

        List<Shelf> shelves = new ArrayList<>();
        NodeList shelvesList = XMLUtils.executeXpath(doc, BASE_TAG + "/user/user_shelves/user_shelf");
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
