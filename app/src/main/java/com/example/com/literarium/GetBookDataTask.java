package com.example.com.literarium;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.dataAcquisition.URLRequestFormatter;
import com.example.com.dataAcquisition.XmlDataParser;
import com.example.com.dataAcquisition.enumType.RequestType;
import com.example.com.dataAcquisition.parseType.Book;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

public class GetBookDataTask extends AsyncTask {

    private Context ref;

    private Document xmlContent;  // content returned by the server

    private HttpRequest httpRequest;

    private int bookId;

    public GetBookDataTask(Context ref, int bookId) {

        this.ref = ref;
        this.bookId = bookId;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String requestUrl = null;
        try {
            requestUrl = URLRequestFormatter.format(RequestType.BOOK_SHOW, String.valueOf(bookId));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("GetBookDataTask", requestUrl);

        httpRequest = new HttpRequest(requestUrl, HttpRequest.HttpRequestMethod.GET);
        httpRequest.send();
        xmlContent = httpRequest.getResult();

        com.example.com.dataAcquisition.parseType.Book book = null;

        try {
            book = XmlDataParser.parseBook(xmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    protected void onPostExecute(Object o) {

        ShowBookActivity concreteActivity = (ShowBookActivity)ref;

        ((ShowBookActivity) ref).loadBookData((Book) o);
    }
}
