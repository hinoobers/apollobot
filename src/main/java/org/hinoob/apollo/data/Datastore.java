package org.hinoob.apollo.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.hinoob.apollo.util.FileUtils;

import java.io.File;

@Getter
public class Datastore {

    private String guildId;
    private JsonObject data;

    public Datastore(String guildId, JsonObject data) {
        this.guildId = guildId;
        this.data = data;
    }

    public String getPrefix() {
        return getValue("prefix", ",");
    }

    public boolean filterEnabled() {
        return data.has("filter") && data.get("filter").getAsBoolean();
    }

    public JsonObject getUserData(String userId) {
        if(!data.has("members")) {
            data.add("members", new JsonArray());
        }
        JsonArray users = data.getAsJsonArray("members");

        for(int i = 0; i < users.size(); i++) {
            JsonObject user = users.get(i).getAsJsonObject();
            if(user.get("id").getAsString().equals(userId)) {
                return user;
            }
        }

        JsonObject user = new JsonObject();
        user.addProperty("id", userId);
        users.add(user);
        return user;
    }

    public int getTickets(String memberId) {
        JsonObject userData = getUserData(memberId);
        if(userData.has("tickets")) {
            return userData.get("tickets").getAsInt();
        } else {
            userData.addProperty("tickets", 0);
            return 0;
        }
    }


    private String getValue(String key, String defaultValue) {
        if(data.has(key)) {
            return data.get(key).getAsString();
        } else {
            data.addProperty(key, defaultValue);
            return defaultValue;
        }
    }

}
