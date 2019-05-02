package com.example.com.geoLocalization;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import static android.content.Context.WIFI_SERVICE;

/**
 * provide some useful information about
 * network stuff.
 */
public class NetworkManager {

    /**
     * returns the IP address of the phone<br>
     * whether it's connected to WiFi or cellular network.
     */
    public static String getIpAddress(Context ctx) throws NetworkErrorException {

        String res = "";

        // get service
        ConnectivityManager CM = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = CM.getActiveNetworkInfo();

        String netState = "";

        if(activeNetworkInfo != null)
            netState = activeNetworkInfo.getTypeName();
        else {
            throw new NetworkErrorException("no network found");
        }


        Log.d("NETSTATE", netState);

        if(netState.equalsIgnoreCase("WIFI")) {
            res = GetDeviceipWiFiData(ctx);
        }

        if(netState.equalsIgnoreCase("MOBILE")) {

            res = GetDeviceipMobileData(ctx);
        }

        return res;
    }

    private static String GetDeviceipMobileData(Context ctx){

        try {
            // loop over all the interfaces of the phone (usually just one)
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();

                    Log.d("POSSIBLE_IP", inetAddress.getHostAddress());

                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {

                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }

        return null;
    }

    private static String GetDeviceipWiFiData(Context ctx)
    {

        WifiManager wm = (WifiManager) ctx.getApplicationContext().getSystemService(WIFI_SERVICE);

        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        return ip;
    }
}
