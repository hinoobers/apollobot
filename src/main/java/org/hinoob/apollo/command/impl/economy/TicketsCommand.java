package org.hinoob.apollo.command.impl.economy;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hinoob.apollo.command.types.TextCommand;
import org.hinoob.apollo.data.Datastore;

import java.util.List;

public class TicketsCommand extends TextCommand {

    @Override
    public List<String> getAliases() {
        return List.of();
    }

    @Override
    public void handle(List<String> arguments, MessageReceivedEvent event, Datastore guildData) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Tickets");
        eb.setDescription("You have " + guildData.getTickets(event.getMember().getId()) + " tickets!");
        eb.setColor(0x00FF00);
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @Override
    public String getName() {
        return "tickets";
    }
}
