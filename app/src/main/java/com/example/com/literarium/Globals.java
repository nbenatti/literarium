package com.example.com.literarium;

import android.app.Application;

public class Globals extends Application {

    public class User {

        private String authToken, userName;
        private int userId;

        public String getAuthToken() {
            return authToken;
        }

        public User() {
        }

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
}
