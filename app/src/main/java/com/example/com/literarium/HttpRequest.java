package com.example.com.literarium;

import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpRequest implements IRequest {

    public enum HttpRequestMethod {
        GET,
        POST
    }

    private String url;

    private HttpRequestMethod method;

    private URL urlObj;

    private String content;

    public HttpRequest(String url, HttpRequestMethod rMethod) {
        this.url = url;
        method = rMethod;
    }

    @Override
    public void send() {
        try {
            urlObj = new URL(this.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Document getResult() {

        InputStream in = null;

        try {
            in = urlObj.openStream();

            return XMLUtils.getNewDocFromStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }
}
