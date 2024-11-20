package org.hinoob.apollo.command.impl;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.hinoob.apollo.command.types.SlashCommand;
import org.hinoob.apollo.data.Datastore;

public class TestSlashCommand extends SlashCommand {

    @Override
    public void handle(SlashCommandInteractionEvent event, Datastore guildData) {
        event.reply("Test slash command executed!").queue();
    }

    @Override
    public String getName() {
        return "testslash";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("testslash", "Test slash demo");
    }
}
