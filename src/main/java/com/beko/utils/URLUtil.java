package com.beko.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ankovalenko on 4/27/2015.
 */
public class URLUtil {

    private URLUtil(){
    }

    public static Map<String, String> extractParametersFromUrl(String url) throws UnsupportedEncodingException {
        Map<String, String> urlParams = new HashMap<String, String>();
        String[] pairs = url.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            urlParams.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return urlParams;
    }
}
