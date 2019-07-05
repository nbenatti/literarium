package com.example.com.bookSharing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import com.example.com.literarium.HttpRequest;
import com.example.com.literarium.R;
import com.example.com.literarium.RequestManager;
import com.example.com.literarium.RequestType;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class DiscardShare extends JobIntentService {

    private static final String TAG = DiscardShare.class.getSimpleName();
    private static final int SERVICE_JOB_ID = 102;

    private List<ShareData> shareDataList;
    private HttpRequest httpRequest;
    private String authToken;

    public DiscardShare() {}

    public static void enqueueWork(Context context, Intent work) {
        Log.d(TAG, "deny task started");
        enqueueWork(context, DiscardShare.class, SERVICE_JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        onHandleIntent(intent);
    }

    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "share denied");

        Bundle b = intent.getExtras();

        shareDataList = b.getParcelableArrayList(getString(R.string.share_data));
        authToken = b.getString(getString(R.string.user_token_setting));

        for(ShareData sd : shareDataList) {
            try {
                String reqUrl = RequestManager.formatRequest(RequestType.DELETE_SHARE,
                        authToken,
                        sd.getUserId(),
                        sd.getBookId());

                Log.d(TAG, reqUrl);

                httpRequest = new HttpRequest(reqUrl, HttpRequest.HttpRequestMethod.GET);
                httpRequest.send();
                httpRequest.getResult();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
