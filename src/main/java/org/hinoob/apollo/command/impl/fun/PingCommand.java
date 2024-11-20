package org.hinoob.apollo.command.impl.fun;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hinoob.apollo.command.types.TextCommand;
import org.hinoob.apollo.data.Datastore;

import java.util.List;

public class PingCommand extends TextCommand {

    @Override
    public List<String> getAliases() {
        return List.of();
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public void handle(List<String> arguments, MessageReceivedEvent event, Datastore guildData) {
        event.getChannel().sendMessage("Pong!").queue();
    }
}
