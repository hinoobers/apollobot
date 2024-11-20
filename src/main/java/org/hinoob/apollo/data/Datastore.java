package org.hinoob.apollo.data;

import com.google.gson.Gson;
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

    private String getValue(String key, String defaultValue) {
        if(data.has(key)) {
            return data.get(key).getAsString();
        } else {
            data.addProperty(key, defaultValue);
            return defaultValue;
        }
    }

}
