package org.hinoob.apollo.command.impl.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hinoob.apollo.command.types.TextCommand;
import org.hinoob.apollo.data.Datastore;
import org.hinoob.apollo.util.EmbedUtil;

import java.util.Arrays;
import java.util.List;

public class ChangePrefixCommand extends TextCommand {

    @Override
    public String getName() {
        return "changeprefix";
    }

    @Override
    public List<String> getAliases() {
        return List.of("cprefix");
    }

    @Override
    public void handle(List<String> arguments, MessageReceivedEvent event, Datastore guildData) {
        if(!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            event.getChannel().sendMessageEmbeds(EmbedUtil.error("Insufficient permissions", "You must have the Manage Server permission to use this command!")).queue();
            return;
        }

        if(arguments.size() != 1) {
            event.getChannel().sendMessageEmbeds(EmbedUtil.error("No prefix provided", "You must provide a new prefix!")).queue();
        } else {
            String prefix = arguments.get(0);
            guildData.getData().addProperty("prefix", prefix);
            event.getChannel().sendMessage("Prefix changed to " + prefix).queue();
        }
    }
}
