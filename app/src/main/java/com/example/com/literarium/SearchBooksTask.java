package com.example.com.literarium;

import android.content.Context;
import android.os.AsyncTask;

import com.example.com.dataAcquisition.URLRequestFormatter;
import com.example.com.dataAcquisition.XmlDataParser;
import com.example.com.localDB.Book;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

public class SearchBooksTask extends AsyncTask {

    private Context ctx;

    private String  keyword;

    private HttpRequest httpRequest;

    private ArrayList<Book> result;

    public SearchBooksTask(Context ctx, String keyword) {
        this.ctx = ctx;
        this.keyword = keyword;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String reqUrl = URLRequestFormatter.format(RequestType., "all", keyword, "1");

        httpRequest = new HttpRequest(reqUrl, HttpRequest.HttpRequestMethod.GET);
        httpRequest.send();
        Document doc = httpRequest.getResult();

        try {
            result = new ArrayList<Book>((ArrayList<Book>)Arrays.asList(XmlDataParser.parseSearch(doc)));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(Object o) {

        SearchActivity act = (SearchActivity)ctx;
    }
}
