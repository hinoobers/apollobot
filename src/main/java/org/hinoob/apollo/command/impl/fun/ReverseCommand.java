package org.hinoob.apollo.command.impl.fun;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hinoob.apollo.command.types.TextCommand;
import org.hinoob.apollo.data.Datastore;

import java.util.List;

public class ReverseCommand extends TextCommand {

    @Override
    public String getName() {
        return "reverse";
    }

    @Override
    public List<String> getAliases() {
        return List.of();
    }

    @Override
    public void handle(List<String> arguments, MessageReceivedEvent event, Datastore guildData) {
        if(arguments.isEmpty()) {
            event.getChannel().sendMessage("You must provide a string to reverse!").queue();
        } else {
            String input = String.join(" ", arguments);
            String reversed = new StringBuilder(input).reverse().toString();
            event.getChannel().sendMessage(reversed).queue();
        }
    }
}
