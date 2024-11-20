package org.hinoob.apollo.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.entities.Guild;
import org.hinoob.apollo.ApolloBot;
import org.hinoob.apollo.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatastoreManager {

    private File file;
    private JsonArray array;
    private final List<Datastore> loadedDatastores = new ArrayList<>();

    public void load() {
        this.file = new File("secure", "datastore.json");
        FileUtils.create(file.getPath());

        try {
            this.array = new Gson().fromJson(FileUtils.readRaw(file.getPath()), JsonArray.class);
        } catch(Exception ignored) {
            this.array = new JsonArray();
        }

        for(Guild guild : ApolloBot.getInstance().getJda().getGuilds()) {
            System.out.println("Loading datastore for " + guild.getName() + " (" + guild.getId() + ")");
            JsonElement o = array.asList().stream().filter(e -> e.getAsJsonObject().get("guild").getAsString().equals(guild.getId())).findFirst().orElse(null);
            if(o == null) {
                JsonObject obj = new JsonObject();
                obj.addProperty("guild", guild.getId());
                obj.add("data", new JsonObject());
                array.add(obj);

                loadedDatastores.add(new Datastore(guild.getId(), obj.getAsJsonObject("data")));
            } else {
                loadedDatastores.add(new Datastore(guild.getId(), o.getAsJsonObject().getAsJsonObject("data")));
            }
        }

        save();
    }

    public Datastore get(String guildId) {
        return loadedDatastores.stream().filter(ds -> ds.getGuildId().equals(guildId)).findFirst().orElse(null);
    }

    public void save() {
        FileUtils.writeRaw(file.getPath(), new Gson().toJson(array));
    }


}
