package com.example.com.bookSharing;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.example.com.literarium.XMLUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
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
                NodeList nl = XMLUtils.executeXpath(response, "response/share");
                if (nl == null)
                    numShares = 0;
                else {
                    shares = XMLUtils.NodeListToListNode(nl);
                    numShares = shares.size();
                }
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

            Log.d("ListenForSharesService", response.getDocumentElement().getTextContent());

            if(numShares > 0) {

                notifyUser();
            }

            timestamp = Globals.getTimestamp();

            try {
                Thread.sleep(SLEEPTIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * notify the user when there are new shares for him.
     */
    private void notifyUser() {
        Log.d("ListenForSharesService", "you have " + numShares + " new shares");

        String notifText = "";

        if(numShares > 1) {
            notifText = "you have " + numShares + " new shares!";
        }
        else {
            notifText = "you have a new share!";
        }

        Intent acceptShareIntent = new Intent(this, AcceptShareBroadcastReceiver.class);
        //acceptShareIntent.setAction();
        PendingIntent acceptSharePendingIntent = PendingIntent.getBroadcast(this, 0, acceptShareIntent, 0);

        Intent denyShareIntent = new Intent(this, DenyShareBroadcastReceiver.class);
        PendingIntent denySharePendingIntent = PendingIntent.getBroadcast(this, 0, denyShareIntent, 0);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.lit_logo_bw)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(notifText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.accept_icon, getString(R.string.accept_share), acceptSharePendingIntent)
                .addAction(R.drawable.deny_icon, getString(R.string.discard_share), denySharePendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
