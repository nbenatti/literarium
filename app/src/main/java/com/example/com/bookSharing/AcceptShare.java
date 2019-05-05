package com.example.com.bookSharing;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

public class AcceptShare extends JobIntentService {

    private static final int SERVICE_JOB_ID = 101;

    public AcceptShare() {
    }

    public static void enqueueWork(Context context, Intent work) {
        Log.d("ListenForSharesService", "work enqueued");
        enqueueWork(context, AcceptShare.class, SERVICE_JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        onHandleIntent(intent);
    }

    protected void onHandleIntent(Intent intent) {
        Log.d("AcceptShare", "share accepted");
    }
}
