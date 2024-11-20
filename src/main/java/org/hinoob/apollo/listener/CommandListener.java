package org.hinoob.apollo.listener;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.hinoob.apollo.ApolloBot;
import org.hinoob.apollo.data.Datastore;
import org.hinoob.apollo.data.DatastoreManager;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        Datastore ds = ApolloBot.getInstance().getDatastoreManager().get(event.getGuild().getId());

        // Show our prefix
        if(event.getMessage().getMentions().getMembers().stream().anyMatch(u -> u.getId().equals(event.getJDA().getSelfUser().getId()))) {
            event.getChannel().sendMessage("```Hello, my prefix here is " + ds.getPrefix() + "```").queue();
            return;
        }

        String raw = event.getMessage().getContentRaw();
        if(raw.startsWith(ds.getPrefix())) {
            ApolloBot.getInstance().getCommandManager().execute(event, ds);
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        Datastore ds = ApolloBot.getInstance().getDatastoreManager().get(event.getGuild().getId());
        ApolloBot.getInstance().getCommandManager().execute(event, ds);
    }
}
