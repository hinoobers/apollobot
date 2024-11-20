package org.hinoob.apollo.listener;

import com.google.gson.Gson;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.hinoob.apollo.ApolloBot;
import org.hinoob.apollo.data.Datastore;
import org.hinoob.apollo.util.EmbedUtil;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFilterListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;

        Datastore ds = ApolloBot.getInstance().getDatastoreManager().get(event.getGuild().getId());
        if(ds.filterEnabled()) {
            // Remove unauthorized links
            try {
                byte[] authorized = MessageFilterListener.class.getResourceAsStream("/authorized_urls.json").readAllBytes();
                List<String> authorizedUrls = new Gson().fromJson(new String(authorized), List.class); // Example data [https://slack.com, https://google.com]
                Pattern pattern = Pattern.compile("https?://(www\\.)?([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+");
                Matcher matcher = pattern.matcher(event.getMessage().getContentRaw());

                while(matcher.find()) {
                    String url = matcher.group();
                    if(!authorizedUrls.contains(url)) {
                        event.getMessage().delete().queue(new Consumer<Void>() {
                            @Override
                            public void accept(Void unused) {
                                event.getChannel().sendMessageEmbeds(EmbedUtil.error("Unauthorized link", "You are not allowed to send unauthorized links!")).queue();
                            }
                        });
                    }
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
