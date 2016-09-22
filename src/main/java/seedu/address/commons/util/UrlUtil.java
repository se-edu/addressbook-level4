package seedu.address.commons.util;

import java.net.URL;

/**
 * An utility class for URL
 */
public class UrlUtil {

    public static boolean compareBaseUrls(URL url1, URL url2) {

        if (url1 == null || url2 == null) {
            return false;
        }
        return url1.getHost().toLowerCase().replaceFirst("www.", "")
                .equals(url2.getHost().replaceFirst("www.", "").toLowerCase())
                && url1.getPath().replaceAll("/", "").toLowerCase()
                .equals(url2.getPath().replaceAll("/", "").toLowerCase());
    }

}
