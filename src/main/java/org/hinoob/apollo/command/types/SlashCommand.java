package org.hinoob.apollo.command.types;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.hinoob.apollo.command.Command;
import org.hinoob.apollo.data.Datastore;

public abstract class SlashCommand implements Command {

    public abstract void handle(SlashCommandInteractionEvent event, Datastore guildData);

    public abstract CommandData getCommandData();
}
