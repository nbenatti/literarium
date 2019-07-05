package com.example.com.literarium;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.parsingData.URLRequestFormatter;
import com.example.com.parsingData.parseType.Book;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import static com.example.com.parsingData.XmlDataParser.parseSearch;

public class SearchBooksTask extends AsyncTask {

    private static final String TAG = SearchBooksTask.class.getSimpleName();

    private Context ctx;
    private String  keyword;
    private HttpRequest httpRequest;
    private String searchFilter;
    private String pageIndex;
    private SearchActivity act;

    public SearchBooksTask(Context ctx, String keyword, String searchFilter, int pageIndex) {
        this.ctx = ctx;
        act = (SearchActivity)ctx;
        this.keyword = keyword;
        this.searchFilter = searchFilter;
        this.pageIndex = String.valueOf(pageIndex);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String reqUrl = null;
        try {
            reqUrl = URLRequestFormatter.format(com.example.com.parsingData.enumType.RequestType.SEARCH_BOOKS, searchFilter, keyword, pageIndex);
            Log.d(TAG, reqUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpRequest = new HttpRequest(reqUrl, HttpRequest.HttpRequestMethod.GET);
        httpRequest.send();
        Document doc = httpRequest.getResult();

        ArrayList<Book> result = null;

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

        ArrayList<Book> result = (ArrayList<Book>)o;

        SearchActivity act = (SearchActivity)ctx;

        act.stopLoadingRing();

        if(result.isEmpty()) {
            act.handleNoResults();
        }
        else
            act.loadData(result);
    }
}
