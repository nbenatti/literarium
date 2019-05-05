package com.example.com.bookSharing;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;

/**
 * synchronizes the
 */
public class SyncSharingsTask extends AsyncTask<String, Void, Integer> {

    private Context ctx;

    private String requestUrl;

    private Document response;

    public SyncSharingsTask(Context ctx) {
        this.ctx = ctx;
        requestUrl = null;
    }

    @Override
    protected Integer doInBackground(String... strings) {

        Log.d("SyncSharingsTask", "task started");

        return 0;
    }

    @Override
    protected void onPostExecute(Integer par) {

    }
}
