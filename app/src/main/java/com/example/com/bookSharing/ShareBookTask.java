package com.example.com.bookSharing;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.geoLocalization.GeoLocalizationActivity;
import com.example.com.literarium.Globals;
import com.example.com.literarium.HttpRequest;
import com.example.com.literarium.R;
import com.example.com.literarium.RequestManager;
import com.example.com.literarium.RequestType;
import com.example.com.parsingData.XMLUtils;

import org.w3c.dom.Document;

import java.io.UnsupportedEncodingException;

import javax.xml.xpath.XPathExpressionException;

public class ShareBookTask extends AsyncTask<Integer, Void, Void> {

    private static final String TAG = ShareBookTask.class.getSimpleName();

    private Context ctx;
    private String requestUrl;
    private SharedPreferences sharedPreferences;

    public ShareBookTask(Context ctx) {
        this.ctx = ctx;

        sharedPreferences = Globals.getSharedPreferences(this.ctx);

        Log.d(TAG, "task started");
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        try {
            requestUrl = RequestManager.formatRequest(RequestType.SHARE_BOOK,
                    sharedPreferences.getString(ctx.getString(R.string.user_token_setting), ""),
                    sharedPreferences.getInt(ctx.getString(R.string.user_id_setting), -1),
                    integers[0],
                    integers[1]);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "share the book " + integers[1] + " to the user " + integers[0]);

        Log.d(TAG, requestUrl);

        HttpRequest httpRequest = new HttpRequest(requestUrl, HttpRequest.HttpRequestMethod.GET);
        httpRequest.send();
        Document response = httpRequest.getResult();

        // get status code
        int statusCode = 0;
        try {
            statusCode = Integer.parseInt(XMLUtils.executeXpath(response, "response/responseCode").item(0).getTextContent());
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        if(statusCode == 200) {

            Log.d(TAG, "book correctly shared");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        GeoLocalizationActivity act = (GeoLocalizationActivity)ctx;
        act.handleBookSharingSuccess();
    }
}
