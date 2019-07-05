package com.example.com.literarium;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.parsingData.URLRequestFormatter;
import com.example.com.parsingData.XmlDataParser;
import com.example.com.parsingData.enumType.RequestType;
import com.example.com.parsingData.parseType.Author;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

public class GetAuthorDataTask extends AsyncTask {

    private static final String TAG = GetAuthorDataTask.class.getSimpleName();

    private Context ref;
    private Document xmlContent;  // content returned by the server
    private HttpRequest httpRequest;
    private int authorId;

    public GetAuthorDataTask(Context ref, int authorId) {
        this.ref = ref;
        this.authorId = authorId;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String requestUrl = null;
        try {
            requestUrl = URLRequestFormatter.format(RequestType.AUTHOR_SHOW, String.valueOf(authorId));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d(TAG, requestUrl);

        httpRequest = new HttpRequest(requestUrl, HttpRequest.HttpRequestMethod.GET);
        httpRequest.send();
        xmlContent = httpRequest.getResult();

        Author author = null;

        try {
            author = XmlDataParser.parseAuthor(xmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return author;
    }

    @Override
    protected void onPostExecute(Object o) {
        AuthorShowActivity concreteActivity = (AuthorShowActivity)ref;

        ((AuthorShowActivity) ref).loadAuthorData((Author) o);
    }
}
