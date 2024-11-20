package org.hinoob.apollo.command.impl;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hinoob.apollo.command.types.TextCommand;
import org.hinoob.apollo.data.Datastore;

import java.util.List;

public class TestCommand extends TextCommand {

    @Override
    public void handle(List<String> arguments, MessageReceivedEvent event, Datastore guildData) {
        event.getChannel().sendMessage("Test command executed!").queue();
        for(String s : arguments) {
            event.getChannel().sendMessage(s).queue();
        }
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public List<String> getAliases() {
        return List.of();
    }
}
