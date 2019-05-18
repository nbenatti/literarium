package com.example.com.literarium;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.parsingData.URLRequestFormatter;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import static com.example.com.parsingData.XmlDataParser.parseSearch;

public class SearchBooksTask extends AsyncTask {

    private Context ctx;

    private String  keyword;

    private HttpRequest httpRequest;

    private ArrayList<com.example.com.parsingData.parseType.Book> result;

    private SearchActivity act;

    public SearchBooksTask(Context ctx, String keyword) {
        this.ctx = ctx;
        act = (SearchActivity)ctx;
        this.keyword = keyword;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String reqUrl = null;
        try {
            reqUrl = URLRequestFormatter.format(com.example.com.parsingData.enumType.RequestType.SEARCH_BOOKS, "all", keyword, "1");
            Log.d("SearchBooksTask", reqUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpRequest = new HttpRequest(reqUrl, HttpRequest.HttpRequestMethod.GET);
        httpRequest.send();
        Document doc = httpRequest.getResult();

        try {
            result = new ArrayList<>(parseSearch(doc));
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

        act.stopLoadingRing();

        act.loadData(result);
    }
}
