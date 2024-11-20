package org.hinoob.apollo.command.impl.moderation;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hinoob.apollo.command.types.TextCommand;
import org.hinoob.apollo.data.Datastore;
import org.hinoob.apollo.util.EmbedUtil;

import java.util.List;

public class ChangePrefixCommand extends TextCommand {

    @Override
    public String getName() {
        return "changeprefix";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public void handle(List<String> arguments, MessageReceivedEvent event, Datastore guildData) {
        if(arguments.size() != 1) {
            event.getChannel().sendMessageEmbeds(EmbedUtil.error("No prefix provided", "You must provide a new prefix!")).queue();
        } else {
            String prefix = arguments.get(0);
            guildData.getData().addProperty("prefix", prefix);
            event.getChannel().sendMessage("Prefix changed to " + prefix).queue();
        }
    }
}
