package org.hinoob.apollo.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.net.URL;
import java.net.URLConnection;

public class HTTPUtil {

    public static JsonElement readJson(String url) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            conn.setRequestProperty("User-Agent", "Apollo discord bot");
            conn.setRequestProperty("Accept", "application/json");
            String s = new String(conn.getInputStream().readAllBytes());
            return new Gson().fromJson(s, JsonElement.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
