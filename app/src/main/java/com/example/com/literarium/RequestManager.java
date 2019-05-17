package com.example.com.literarium;

import org.apache.commons.lang3.ClassUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * formats all the non-parsing-related XML requests involved in the application.
 */
public class RequestManager {

    public static String formatRequest(RequestType type, Object ... args) throws UnsupportedEncodingException {

        String req = "";

        String[] urlEncodedArgs = new String[args.length];
        for(int i = 0; i < args.length; ++i) {
            if(args[i] instanceof String)
                urlEncodedArgs[i] = URLEncoder.encode((String)args[i], java.nio.charset.StandardCharsets.UTF_8.toString());
            else if(ClassUtils.isPrimitiveOrWrapper(args[i].getClass()))
                urlEncodedArgs[i] = URLEncoder.encode(String.valueOf(args[i]), java.nio.charset.StandardCharsets.UTF_8.toString());
        }


        if(type == RequestType.GEO_REPORT) {
            req = "http://79.40.96.138/literarium_api/geo_report.php?userid=" + URLEncoder.encode(urlEncodedArgs[0], java.nio.charset.StandardCharsets.UTF_8.toString());
        } else if(type == RequestType.LOG_POSITION) {
            req = "http://79.40.96.138/literarium_api/insert_geo_data.php?"
                    + "userid=" + urlEncodedArgs[0]
                    + "&latitudine=" + urlEncodedArgs[1]
                    + "&longitudine=" + urlEncodedArgs[2]
                    + "&indirizzo=" + urlEncodedArgs[3];
        } else if(type == com.example.com.literarium.RequestType.AUTH_USER) {

            req = "http://79.40.96.138/literarium_api/auth.php?"
                    +"nomeutente=" + urlEncodedArgs[0]
                    +"&password=" + urlEncodedArgs[1];
        } else if(type == RequestType.SHARE_BOOK) {

            req = "http://79.40.96.138/literarium_api/share_book.php?"
                    + "token=" + urlEncodedArgs[0]
                    + "&userid=" + urlEncodedArgs[1]
                    + "&receiverid=" + urlEncodedArgs[2]
                    + "&bookid=" + urlEncodedArgs[3];
        } else if(type == RequestType.GET_NEW_SHARES) {

            req = "http://79.40.96.138/literarium_api/get_new_shares.php?"
                    + "token=" + urlEncodedArgs[0]
                    + "&userid=" + urlEncodedArgs[1]
                    + "&timestamp=" + urlEncodedArgs[2];
        } else if(type == RequestType.DELETE_SHARE) {

            req = "http://79.40.96.138/literarium_api/delete_share.php?"
                    + "token=" + urlEncodedArgs[0]
                    + "&receiverid=" + urlEncodedArgs[1]
                    + "&bookid=" + urlEncodedArgs[2];
        } else if(type == RequestType.REGISTER) {

            req = "http://79.40.96.138/literarium_api/register.php?"
                    + "username=" + urlEncodedArgs[0]
                    + "&password=" + urlEncodedArgs[1];
        }

        return req;
    }

}
