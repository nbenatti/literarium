package com.example.com.literarium;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.parsingData.URLRequestFormatter;
import com.example.com.parsingData.XmlDataParser;
import com.example.com.parsingData.enumType.RequestType;
import com.example.com.parsingData.parseType.User;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

public class GetUserInfoTask extends AsyncTask {

    private Context ref;

    private Document xmlContent;  // content returned by the server

    private HttpRequest httpRequest;

    private String userName;

    public GetUserInfoTask(Context ref, String userName) {
        this.ref = ref;
        this.userName = userName;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        Log.d("GetUserInfoTask", "task started");

        String requestUrl = null;
        try {
            requestUrl = URLRequestFormatter.format(RequestType.USER_INFO, String.valueOf(userName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("GetBookDataTask", requestUrl);

        httpRequest = new HttpRequest(requestUrl, HttpRequest.HttpRequestMethod.GET);
        httpRequest.send();
        xmlContent = httpRequest.getResult();

        User user = null;

        try {
            user = XmlDataParser.parseUserInfo(xmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        // user not found
        if(user == null) {

            return null;
        }

        return user;
    }

    @Override
    protected void onPostExecute(Object o) {

        UserShowActivity concreteActivity = (UserShowActivity) ref;


        if(o != null) {
            ((UserShowActivity) ref).loadUserData((User) o);
        }
        else if(o == null) {

            concreteActivity.handleUserNotFound();
        }
    }
}
