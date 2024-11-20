package org.hinoob.apollo.command.impl.fun;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hinoob.apollo.command.types.TextCommand;
import org.hinoob.apollo.data.Datastore;
import org.hinoob.apollo.util.EmbedUtil;

import java.util.Arrays;
import java.util.List;

public class CoinflipCommand extends TextCommand {

    @Override
    public List<String> getAliases() {
        return List.of();
    }

    @Override
    public String getName() {
        return "coinflip";
    }

    @Override
    public void handle(List<String> arguments, MessageReceivedEvent event, Datastore guildData) {
        String bet = arguments.isEmpty() ? "none" : String.join(" ", arguments).toLowerCase();
        String[] outcomes = {"heads", "tails"};
        String outcome = outcomes[(int) (Math.random() * outcomes.length)];

        if(bet.equals("none")) {
            event.getChannel().sendMessage("It landed on " + outcome).queue();
            event.getChannel().sendMessage(":coin:").queue();;
        } else {
            if(!(bet.equalsIgnoreCase("h") || bet.equalsIgnoreCase("t") || bet.equalsIgnoreCase("heads") || bet.equalsIgnoreCase("tails"))) {
                event.getChannel().sendMessageEmbeds(EmbedUtil.error("Invalid bet", "You must bet on heads or tails!")).queue();
                return;
            }

            int tickets = guildData.getTickets(event.getMember().getId());
            if(tickets < 5) {
                event.getChannel().sendMessageEmbeds(EmbedUtil.error("Insufficient tickets", "You need at least 5 tickets to play!")).queue();
                return;
            }

            if(bet.equals(outcome)) {
                event.getChannel().sendMessage("You won!").queue();

                guildData.getUserData(event.getMember().getId()).addProperty("tickets", tickets + 5);
            } else {
                event.getChannel().sendMessage("You lost!").queue();

                guildData.getUserData(event.getMember().getId()).addProperty("tickets", tickets - 5);
            }
            event.getChannel().sendMessage("It landed on " + outcome + " and you bet on " + bet).queue();
            event.getChannel().sendMessage(":coin:").queue();;
        }
    }
}
