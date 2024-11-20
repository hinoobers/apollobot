package org.hinoob.apollo.command.types;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hinoob.apollo.command.Command;
import org.hinoob.apollo.data.Datastore;

import java.util.List;

public abstract class TextCommand implements Command {

    public abstract void handle(List<String> arguments, MessageReceivedEvent event, Datastore guildData);
}
