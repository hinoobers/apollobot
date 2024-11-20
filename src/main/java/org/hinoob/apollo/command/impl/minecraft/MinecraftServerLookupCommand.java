package org.hinoob.apollo.command.impl.minecraft;

import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hinoob.apollo.command.types.TextCommand;
import org.hinoob.apollo.data.Datastore;
import org.hinoob.apollo.util.EmbedUtil;
import org.hinoob.apollo.util.HTTPUtil;

import java.util.List;

public class MinecraftServerLookupCommand extends TextCommand {

    @Override
    public void handle(List<String> arguments, MessageReceivedEvent event, Datastore guildData) {
        if(arguments.size() != 1) {
            event.getChannel().sendMessageEmbeds(EmbedUtil.error("Invalid arguments", "You must provide a server IP!")).queue();
        } else {
            String ip = arguments.get(0);

            JsonObject o = HTTPUtil.readJson("https://api.mcsrvstat.us/3/" + ip).getAsJsonObject();
            if(o.get("online").getAsBoolean()) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Server Information");
                eb.addField("IP", ip, false);
                eb.addField("Players", o.get("players").getAsJsonObject().get("online").getAsInt() + "/" + o.get("players").getAsJsonObject().get("max").getAsInt(), false);
                eb.addField("Version", o.get("version").getAsString(), false);
                eb.setColor(0x00FF00);

                event.getChannel().sendMessageEmbeds(eb.build()).queue();
            } else {
                event.getChannel().sendMessageEmbeds(EmbedUtil.error("Server offline", "The server is offline!")).queue();
            }

        }
    }

    @Override
    public String getName() {
        return "mcsrv";
    }

    @Override
    public List<String> getAliases() {
        return List.of("mcserver");
    }
}
