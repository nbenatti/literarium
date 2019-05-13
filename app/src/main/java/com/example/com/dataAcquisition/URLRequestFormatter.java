package com.example.com.dataAcquisition;

import com.example.com.dataAcquisition.enumType.RequestType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class URLRequestFormatter {

    private static final String KEY = "MJt9vmrPRlZy1k1tGcooHg";
    private static final String BASE = "https://www.goodreads.com/";

    public static String format(RequestType type, String... parameters) throws UnsupportedEncodingException {
        switch (type) {
            case AUTHOR_SHOW:
                return BASE + "author/show/" + parameters[0] + "?key=" + KEY;
            case BOOK_SHOW:
                return BASE + "book/show/" + parameters[0] + "?key=" + KEY;
            case SEARCH_BOOKS:
                String query = URLEncoder.encode(parameters[1], "UTF-8");
                return BASE + "search/index?search[field]=" + parameters[0] + "&q=" + query + "&page=" + parameters[2] + "&key=" + KEY;
            case USER_INFO:
                return BASE + "user/show/?username=" + parameters[0] + "&key=" + KEY;
            default:
                throw new FormatterException("Unreachable statement reached");
        }
    }
}
