package com.example.com.literarium;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.parsingData.XMLUtils;

import org.w3c.dom.Document;

import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

public class LoginTask extends AsyncTask {

    private Context ref;

    private String userName, password;

    private SharedPreferences sharedPreferences;

    public LoginTask(Context ref, String userName, String password) {
        this.ref = ref;
        this.userName = userName;
        this.password = password;

        sharedPreferences = ref.getSharedPreferences(ref.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        // authenticate the user via the webservice
        try {
            HttpRequest httpRequest = new HttpRequest(RequestManager.formatRequest(RequestType.AUTH_USER, userName, password),
                    HttpRequest.HttpRequestMethod.GET);
            Log.d("LoginTask", RequestManager.formatRequest(RequestType.AUTH_USER, userName, password));
            httpRequest.send();
            Document response = httpRequest.getResult();

            String stringifiedXml = XMLUtils.docToString(response);
            Log.d("LoginTask", stringifiedXml);

            // get authentication esit
            String authEsit = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(response, "response/responseCode")).get(0).getTextContent();

            // authentication failes
            if(Integer.parseInt(authEsit) == 500) {
                return false;
            }

            // get user token
            String token = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(response, "response/authToken")).get(0).getTextContent();
            String userId = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(response, "response/userId")).get(0).getTextContent();

            Log.d("AUTH_TOKEN", token);

            // insert user's data in the global area
            /*Globals.getInstance().getUserLocalData().setAuthToken(token);
            Globals.getInstance().getUserLocalData().setUserName(userName);
            Globals.getInstance().getUserLocalData().setUserId(Integer.parseInt(userId));*/

            // insert user's data in the shared preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(ref.getString(R.string.user_id_setting), Integer.parseInt(userId));
            editor.putString(ref.getString(R.string.user_token_setting), token);
            editor.putString(ref.getString(R.string.username_setting), userName);
            editor.commit();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPostExecute(Object o) {

        Boolean loginStatus = (Boolean)o;

        LoginActivity activity = (LoginActivity)ref;

        if(loginStatus) {
            activity.handleLoginSuccess();
        }
        else {
            activity.handleLoginFailure();
        }
    }
}
