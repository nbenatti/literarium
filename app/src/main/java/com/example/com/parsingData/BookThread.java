package com.example.com.parsingData;

import com.example.com.literarium.HttpRequest;
import com.example.com.parsingData.enumType.RequestType;
import com.example.com.parsingData.parseType.Book;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

public final class BookThread extends Thread {

    private String bookId;
    private Book book = null;

    public BookThread(String bookId){
        this.bookId = bookId;
    }

    @Override
    public void run() {
        String url = null;
        try {
            url = URLRequestFormatter.format(RequestType.BOOK_SHOW, bookId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpRequest httpRequest = new HttpRequest(url, HttpRequest.HttpRequestMethod.GET);
        httpRequest.send();
        try {
            book = XmlDataParser.parseBook(httpRequest.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    public Book getBook() {
        return book;
    }
}
