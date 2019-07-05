package com.example.com.bookSharing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.com.literarium.Globals;
import com.example.com.literarium.R;

public class BootBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = BootBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "phone booted up", Toast.LENGTH_LONG).show();
        Log.d(TAG, "phone booted up");

        /*ComponentName comp = new ComponentName(context.getPackageName(),
                                                ListenForSharesService.class.getName());*/

        // create a notification channel
        Globals.createNotificationChannel(context);

        // get app settings even if the app is not running
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key),
                                                                           Context.MODE_PRIVATE);
        String lastAccessTimestamp = sharedPreferences.getString(context.getString(R.string.last_access_setting), "");
        int userId = sharedPreferences.getInt(context.getString(R.string.user_id_setting), 0);
        String userToken = sharedPreferences.getString(context.getString(R.string.user_token_setting), "");

        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putString(context.getString(R.string.last_access_setting), lastAccessTimestamp);
        b.putInt(context.getString(R.string.user_id_setting), userId);
        b.putString(context.getString(R.string.user_token_setting), userToken);
        i.putExtras(b);

        /*JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(Integer.parseInt(context.getString(R.string.job_id_lfs)), new ComponentName(context, ListenForSharesService.class));

        JobInfo jobInfo = jobInfoBuilder.setPersisted(true).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).setRequiresCharging(true).setExtras(b).setPeriodic(10000).build();

        JobScheduler jobScheduler = (JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);*/

        ListenForSharesService.enqueueWork(context, i);
    }
}
