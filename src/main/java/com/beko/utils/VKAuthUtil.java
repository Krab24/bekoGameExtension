package com.beko.utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ankovalenko on 4/27/2015.
 */
public class VKAuthUtil {

    private static final String VK_API_VERSION = "5.28";
    private static final String APP_ID = "4890945";
    private static final String APP_SECRET_KEY = "NnGTeM17WRcoVoavV1XJ";

    private VKAuthUtil(){
    }

    public static boolean isVKParamsValid(String userId, String token) throws IOException, ParseException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https").setHost("api.vk.com").setPath("/method/users.get")
                .setParameter("user_id", userId)
                .setParameter("access_token", token)
                .setParameter("v", VK_API_VERSION);
        HttpResponse response = HttpConnectionAgent.connectResponse(uriBuilder);
        StringWriter content = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), content);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(content.getBuffer().toString());
        JSONObject jsonObject = (JSONObject) obj;
        return response.getStatusLine().getStatusCode() == 200 && jsonObject!=null && jsonObject.containsKey("response") && !jsonObject.containsKey("error");
    }

    public static boolean isVKAuthKeyValid(String authKey, String userId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String text = APP_ID+"_"+userId+"_"+APP_SECRET_KEY;
        String result = cryptWithMD5(text);
        return authKey.equals(result);
    }

    private static String cryptWithMD5(String text) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(text.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }
        return hashtext;

    }
}
