package com.example.com.literarium;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.UnsupportedEncodingException;

public class RegisterTask  extends AsyncTask {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private Context ctx;
    private HttpRequest httpRequest;
    private String userName, password, grUsername;

    public RegisterTask(Context ctx, String userName, String grUsername, String password) {
        this.userName = userName;
        this.password = password;
        this.grUsername = grUsername;
        this.ctx = ctx;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            String reqUrl = RequestManager.formatRequest(RequestType.REGISTER, userName, grUsername, password);

            httpRequest = new HttpRequest(reqUrl, HttpRequest.HttpRequestMethod.GET);
            httpRequest.send();
            Document doc = httpRequest.getResult();

            Log.d(TAG, doc.getDocumentElement().getTextContent());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

        RegisterActivity act = (RegisterActivity)ctx;

        ((RegisterActivity) ctx).handleRegisterSuccess();
    }
}
