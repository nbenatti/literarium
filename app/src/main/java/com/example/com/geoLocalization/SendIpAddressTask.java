package com.example.com.geoLocalization;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SendIpAddressTask extends AsyncTask {

    private static final String TAG = SendIpAddressTask.class.getSimpleName();
    private final String REQUEST_NAME = "PHONE_IP_UPDATE";

    private Context ref;
    private Exception lastThrownException;
    private Socket s;

    //private SettingsReader settingsReader;

    public SendIpAddressTask(Context ref) {

        this.ref = ref;

        /*try {
            settingsReader = SettingsReader.getInstance();
        } catch (IOException e) {
            lastThrownException = e;
        }*/

        Log.d(TAG, "task started");
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String phoneIp = "";

        try {
            phoneIp = NetworkManager.getIpAddress(ref);
        } catch (NetworkErrorException e) {
            lastThrownException = e;
            return null;
        }

        Log.d(TAG, "phone IP is " + phoneIp);

        try {

            s = new Socket(/*settingsReader.getSetting("SERVER:IP")*/"95.236.97.101",
                    /*Integer.parseInt(settingsReader.getSetting("SERVER_PORT"))*/6000);

            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            pw.println(REQUEST_NAME);
            pw.println(phoneIp);
            pw.close();

            Log.d(TAG, "data sent");

            s.close();
        }
        catch(IOException e) {
            lastThrownException = e;
            return null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

        if(lastThrownException != null) {

            // manage exception in UI
        }

        Log.d(TAG, "task finished");
    }
}
