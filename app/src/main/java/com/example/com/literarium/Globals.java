package com.example.com.literarium;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Globals extends Application {

    public class User {

        private String authToken, userName;
        private int userId;

        public String getAuthToken() {
            return authToken;
        }

        public User() {}

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    private User userLocalData;

    private static Globals instance;

    private Globals() {
        userLocalData = new User();
    }

    public static Globals getInstance() {
        if(instance == null)
            instance = new Globals();
        return instance;
    }

    public User getUserLocalData() {
        return userLocalData;
    }

    public void setUserLocalData(User userLocalData) {
        this.userLocalData = userLocalData;
    }

    public static void setInstance(Globals instance) {
        Globals.instance = instance;
    }

    public static void createNotificationChannel(Context ctx) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ctx.getString(R.string.channel_name);
            String description = ctx.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(ctx.getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static String getTimestamp() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());

        return timestamp;
    }

    public static SharedPreferences getSharedPreferences(Context ctx) {

        //if user had already logged in, skip to the main activity
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(ctx.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        return sharedPreferences;
    }

}
