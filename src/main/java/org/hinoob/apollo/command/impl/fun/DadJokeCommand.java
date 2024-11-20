package org.hinoob.apollo.command.impl.fun;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hinoob.apollo.command.impl.TestCommand;
import org.hinoob.apollo.data.Datastore;
import org.hinoob.apollo.util.HTTPUtil;

import java.util.List;

public class DadJokeCommand extends TestCommand {

    @Override
    public String getName() {
        return "dadjoke";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public void handle(List<String> arguments, MessageReceivedEvent event, Datastore guildData) {
        event.getChannel().sendMessage(HTTPUtil.readJson("https://icanhazdadjoke.com").getAsJsonObject().get("joke").getAsString()).queue();
    }
}
