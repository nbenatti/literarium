package literarium.parsingData;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class URLRequestFormatter {

    private static final String KEY = "MJt9vmrPRlZy1k1tGcooHg";
    private static final String BASE = "https://www.goodreads.com/";

    public static String format(RequestType type, String... parameters) {
        switch (type) {
            case BOOK_SHOW:
                return BASE + "book/show/" + parameters[0] + "?format=xml&key=" + KEY;
            case AUTHOR_SHOW:
                return BASE + "author/show/" + parameters[0] + "?format=xml&key=" + KEY;
            case AUTHOR_BOOKS:
                return BASE + "author/show/" + parameters[0] + "?format=xml&key=" + KEY + ((parameters.length > 1) ? "&page=" + parameters[1] : "");
            case SEARCH_BOOKS:
                try {
                    return BASE + "search/index.xml?key=" + KEY + "&q=" + URLEncoder.encode(parameters[0], "UTF8") + ((parameters.length>1) ? "&page=" + parameters[1] : "") + ((parameters.length>2) ? "&search[field]=" + parameters[2] : "");
                } catch (UnsupportedEncodingException e) {}
            default:
                return "";
        }
    }
}
