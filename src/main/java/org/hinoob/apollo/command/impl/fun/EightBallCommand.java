package org.hinoob.apollo.command.impl.fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hinoob.apollo.command.types.TextCommand;
import org.hinoob.apollo.data.Datastore;
import org.hinoob.apollo.util.EmbedUtil;

import java.util.List;

public class EightBallCommand extends TextCommand {

    @Override
    public List<String> getAliases() {
        return List.of();
    }

    @Override
    public String getName() {
        return "8ball";
    }

    @Override
    public void handle(List<String> arguments, MessageReceivedEvent event, Datastore guildData) {
        if(arguments.isEmpty()) {
            event.getChannel().sendMessageEmbeds(EmbedUtil.error("No question provided", "You must provide a question!")).queue();
            return;
        }
        String question = String.join(" ", arguments);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("8ball");
        eb.addField("Question", question.endsWith("?") ? question : question + "?", false);
        eb.addField("Answer", getAnswer(), false);
        eb.setColor(0x00FF00);
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    private String getAnswer() {
        String[] answers = new String[] {
            "It is certain",
            "It is decidedly so",
            "Without a doubt",
            "Yes, definitely",
            "You may rely on it",
            "As I see it, yes",
            "Most likely",
            "Outlook good",
            "Yes",
            "Signs point to yes",
            "Reply hazy, try again",
            "Ask again later",
            "Better not tell you now",
            "Cannot predict now",
            "Concentrate and ask again",
            "Don't count on it",
            "My reply is no",
            "My sources say no",
            "Outlook not so good",
            "Very doubtful"
        };
        return answers[(int) (Math.random() * answers.length)];
    }
}
