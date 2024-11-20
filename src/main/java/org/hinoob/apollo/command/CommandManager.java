package org.hinoob.apollo.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.hinoob.apollo.ApolloBot;
import org.hinoob.apollo.command.impl.TestCommand;
import org.hinoob.apollo.command.impl.TestSlashCommand;
import org.hinoob.apollo.command.impl.economy.TicketsCommand;
import org.hinoob.apollo.command.impl.fun.*;
import org.hinoob.apollo.command.impl.minecraft.MinecraftServerLookupCommand;
import org.hinoob.apollo.command.impl.moderation.ChangePrefixCommand;
import org.hinoob.apollo.command.types.SlashCommand;
import org.hinoob.apollo.command.types.TextCommand;
import org.hinoob.apollo.data.Datastore;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private final List<Command> commands = new ArrayList<>();

    public void load() {
//        commands.add(new TestCommand());
//        commands.add(new TestSlashCommand());
        commands.add(new ChangePrefixCommand());
        commands.add(new DadJokeCommand());
        commands.add(new CoinflipCommand());
        commands.add(new EightBallCommand());
        commands.add(new ReverseCommand());
        commands.add(new PingCommand());
        commands.add(new TicketsCommand());

        commands.add(new MinecraftServerLookupCommand());


        // Guild-only
        CommandListUpdateAction action = ApolloBot.getInstance().getJda().updateCommands();
        for(Command command : this.commands) {
            if(command instanceof SlashCommand slshcmd) {
                action.addCommands(slshcmd.getCommandData()).queue();
            }
        }

        action.queue();
    }

    public void execute(MessageReceivedEvent event, Datastore guildData) {
        String raw = event.getMessage().getContentRaw();

        List<String> args = parse(raw);
        for(Command command : this.commands) {
            if(command instanceof TextCommand txtcmd) {
                if(!raw.startsWith(guildData.getPrefix() + txtcmd.getName())) {
                    boolean a = false;
                    for(String alias : txtcmd.getAliases()) {
                        if(raw.startsWith(guildData.getPrefix() + alias)) {
                            a = true;
                            break;
                        }
                    }

                    if(!a) {
                        continue;
                    }
                }

                txtcmd.handle(args, event, guildData);
            }
        }
    }

    public void execute(SlashCommandInteractionEvent event, Datastore guildData) {
        for(Command command : this.commands) {
            if(command instanceof SlashCommand slshcmd) {
                if(slshcmd.getCommandData().getName().equals(event.getName())) {
                    slshcmd.handle(event, guildData);
                }
            }
        }
    }

    private List<String> parse(String input) {
        List<String> args = new ArrayList<>();

        if(!input.isEmpty() && input.indexOf(' ') != -1) {
            boolean quotes = false;
            StringBuilder currentArg = new StringBuilder();

            for (int i = input.indexOf(' ') + 1; i < input.length(); i++) {
                char c = input.charAt(i);

                if (c == '"') {
                    quotes = !quotes;
                    if (currentArg.length() > 0) {
                        args.add(currentArg.toString());
                        currentArg.setLength(0);
                    }
                } else if (Character.isWhitespace(c) && !quotes) {
                    if (currentArg.length() > 0) {
                        args.add(currentArg.toString());
                        currentArg.setLength(0);
                    }
                } else {
                    currentArg.append(c);
                }
            }

            if (currentArg.length() > 0) {
                args.add(currentArg.toString());
            }
        }
        return args;
    }
}
