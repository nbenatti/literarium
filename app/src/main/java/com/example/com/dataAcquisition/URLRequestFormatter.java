package com.example.com.literarium;

import com.example.com.literarium.RequestType;

/**
 *
 * @author Francesco Rocca
 */
public class URLRequestFormatter {

    private static final String KEY = "MJt9vmrPRlZy1k1tGcooHg";
    private static final String BASE = "https://www.goodreads.com/";

    public static String format(RequestType type, String... parameters) {
        switch (type) {
            case AUTHOR_SHOW:
                return BASE + "author/show/" + parameters[0] + "?format=xml&key=" + KEY;
            case AUTHOR_BOOKS:
                return BASE + "author/show/" + parameters[0] + "?format=xml&key=" + KEY + "&page=" + parameters[1];
            case SEARCH_AUTHOR:
                return BASE + "api/author_url/" + parameters[0] + "?key=" + KEY;
            case SEARCH_BOOKS:
                return BASE + "search/index.xml?key=" + KEY + "&q=" + parameters[0] + "&page=" + parameters[1] + "&search[field]=" + parameters[2];
            default:
                return "";
        }
    }
}
