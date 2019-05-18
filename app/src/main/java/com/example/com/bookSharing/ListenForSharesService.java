package com.example.com.bookSharing;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.com.literarium.Globals;
import com.example.com.literarium.HttpRequest;
import com.example.com.literarium.R;
import com.example.com.literarium.RequestManager;
import com.example.com.literarium.RequestType;
import com.example.com.parsingData.ParseUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

public class ListenForSharesService extends JobIntentService {

    private final int SLEEPTIME = 10000;

    private static final int SERVICE_JOB_ID = 100;

    private final int NOTIFICATION_ID = 72;

    private Context ctx;

    private String requestUrl;

    private Document response;

    private boolean stop;

    /**
     * application settings.
     */
    private static SharedPreferences sharedPreferences;

    /**
     * timestamp of the last request
     */
    private String timestamp;

    /**
     * data of the currently logged-in user.
     */
    private int userId;
    private String userToken;

    /**
     * number of new shares since the last query to the DB.
     */
    private int numShares;

    public ListenForSharesService() {
        super();
    }

    public ListenForSharesService(String name) {
        super();

        stop = false;
    }

    public static void enqueueWork(Context context, Intent work) {
        Log.d("ListenForSharesService", "work enqueued");

        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        enqueueWork(context, ListenForSharesService.class, SERVICE_JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        onHandleIntent(intent);
    }

    protected void onHandleIntent(Intent intent) {

        Log.d("ListenForSharesService", "service started");

        // === fetch user data from preferences ===

        Bundle b = intent.getExtras();

        timestamp = b.getString(getString(R.string.last_access_setting));
        Log.d("ListenForSharesService", "last access timestamp " + timestamp);

        userId = b.getInt(getString(R.string.user_id_setting));
        Log.d("ListenForSharesService", "user id " + userId);

        userToken = b.getString(getString(R.string.user_token_setting));
        Log.d("ListenForSharesService", "user token " + userToken);

        while(!stop) {

            try {
                requestUrl = RequestManager.formatRequest(RequestType.GET_NEW_SHARES, userToken, userId, timestamp);
                Log.d("ListenForSharesService", requestUrl);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            HttpRequest httpRequest = new HttpRequest(requestUrl, HttpRequest.HttpRequestMethod.GET);
            httpRequest.send();
            response = httpRequest.getResult();

            // get the list of shares
            List<Node> shares = null;
            try {
                NodeList nl = ParseUtils.executeXpath(response, "response/share");
                if (nl == null)
                    numShares = 0;
                else {
                    shares = ParseUtils.NodeListToListNode(nl);
                    numShares = shares.size();

                    List<ShareData> shareDataList = new ArrayList<>();

                    // get data
                    for(int i = 0; i < numShares; ++i) {

                        Element el = ((Element)shares.get(i));

                        String userId = el.getElementsByTagName("userId").item(0).getTextContent();
                        String bookId = el.getElementsByTagName("bookId").item(0).getTextContent();
                        shareDataList.add(new ShareData(userId, bookId));
                    }

                    Log.d("ListenForSharesService", response.getDocumentElement().getTextContent());

                    if(numShares > 0) {

                        notifyUser(shareDataList);
                    }
                }
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

            timestamp = Globals.getTimestamp();

            try {
                Thread.sleep(SLEEPTIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*stop = isMyServiceRunning(ListenForSharesService.class);
            Log.d("ListenForSharesService", "is my service running: " + stop);*/
        }

        updateLastAccessTimestamp();
    }

    @Override
    public boolean onStopCurrentWork() {

        updateLastAccessTimestamp();
        return true;
    }

    @Override
    public void onDestroy() {

        updateLastAccessTimestamp();
        super.onDestroy();
    }

    private void updateLastAccessTimestamp() {
        String timestamp = Globals.getTimestamp();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.last_access_setting), timestamp);
        editor.commit();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * notify the user when there are new shares for him.
     */
    private void notifyUser(List<ShareData> shareDataList) {

        Log.d("ListenForSharesService", "you have " + numShares + " new shares");

        String notifText = "";

        if(numShares > 1) {
            notifText = "you have " + numShares + " new shares!";
        }
        else {
            notifText = "you have a new share!";
        }

        Intent acceptShareIntent = new Intent(this, AcceptShareBroadcastReceiver.class);
        acceptShareIntent.setAction(getString(R.string.accept_share_broadcast));

        // send share data to the intent
        acceptShareIntent.putParcelableArrayListExtra(getString(R.string.share_data), (ArrayList<ShareData>)shareDataList);
        acceptShareIntent.putExtra(getString(R.string.user_id_setting), userId);
        acceptShareIntent.putExtra(getString(R.string.user_token_setting), userToken);

        PendingIntent acceptSharePendingIntent = PendingIntent.getBroadcast(this, 0, acceptShareIntent, 0);

        Intent discardShareIntent = new Intent(this, DiscardShareBroadcastReceiver.class);
        discardShareIntent.setAction(getString(R.string.discard_share_broadcast));

        // send share data to the intent
        discardShareIntent.putParcelableArrayListExtra(getString(R.string.share_data), (ArrayList<ShareData>)shareDataList);
        discardShareIntent.putExtra(getString(R.string.user_id_setting), userId);
        discardShareIntent.putExtra(getString(R.string.user_token_setting), userToken);

        PendingIntent discardSharePendingIntent = PendingIntent.getBroadcast(this, 0, discardShareIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.lit_logo_yellow)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(notifText)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .addAction(R.drawable.accept_icon, getString(R.string.accept_share), acceptSharePendingIntent)
                .addAction(R.drawable.deny_icon, getString(R.string.discard_share), discardSharePendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
