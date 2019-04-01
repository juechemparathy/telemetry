package fyi.jue.mobile.android.telemetry.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class StringUtils {

    /**
     * Check for the inputString limit, else throw IllegalArgumentException.
     *
     * @param input
     * @param lengthLimit
     * @return String valid input string
     */
    public static String assertLength(String input, int lengthLimit) {
        if (input == null || input.length() >= lengthLimit) {
            throw new IllegalArgumentException("Input string is null or exceed limit");
        }
        return input;
    }

    private static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String urlEncodeUTF8(StringBuilder queryString, Map<?, ?> map) {
        if (queryString == null) {
            queryString = new StringBuilder();
        }
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (queryString.length() > 0) {
                queryString.append("&");
            }
            queryString.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return queryString.toString();
    }
}
