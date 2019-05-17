package com.example.com.literarium;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.UnsupportedEncodingException;

public class RegisterTask  extends AsyncTask {

    private Context ctx;

    private HttpRequest httpRequest;

    private String userName, password;

    public RegisterTask(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            String reqUrl = RequestManager.formatRequest(RequestType.REGISTER, userName, password);

            httpRequest = new HttpRequest(reqUrl, HttpRequest.HttpRequestMethod.GET);
            httpRequest.send();
            Document doc = httpRequest.getResult();

            Log.d("RegisterTask", doc.getDocumentElement().getTextContent());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        return null;
    }
}
