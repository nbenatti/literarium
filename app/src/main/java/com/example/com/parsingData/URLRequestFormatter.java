package app.literarium.parsingData;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import app.literarium.parsingData.enumType.RequestType;

public final class URLRequestFormatter {

    private static final String[] KEY = {"MJt9vmrPRlZy1k1tGcooHg", "X8Z4I0uwTHh2hcos96dJA", "MJt9vmrPRIZy1k1tGcooHg"};
    private static final String BASE = "https://www.goodreads.com/";
    private static int i = 0;

    public static String format(RequestType type, String... parameters) throws UnsupportedEncodingException {
        String key = KEY[i];
        i = (i + 1) % KEY.length;
        switch (type) {
            case AUTHOR_SHOW:
                return BASE + "author/show/" + parameters[0] + "?key=" + key;
            case BOOK_SHOW:
                return BASE + "book/show/" + parameters[0] + "?key=" + key;
            case SEARCH_BOOKS:
                String query = URLEncoder.encode(parameters[1], "UTF-8");
                return BASE + "search/index?search[field]=" + parameters[0] + "&q=" + query + "&page=" + parameters[2] + "&key=" + key;
            case USER_INFO:
                return BASE + "user/show/?username=" + parameters[0] + "&key=" + key;
            default:
                throw new FormatterException("Unreachable statement reached");
        }
    }
}
