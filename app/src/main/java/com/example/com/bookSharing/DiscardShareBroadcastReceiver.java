package com.example.com.bookSharing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.com.literarium.R;

public class DiscardShareBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = DiscardShareBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "discard receiver started");
        if(context.getString(R.string.discard_share_broadcast).equals(intent.getAction()))
            DiscardShare.enqueueWork(context, intent);
    }
}
