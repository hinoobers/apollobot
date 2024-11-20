package org.hinoob.apollo;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.hinoob.apollo.command.CommandManager;
import org.hinoob.apollo.data.DatastoreManager;
import org.hinoob.apollo.listener.CommandListener;
import org.hinoob.apollo.util.FileUtils;

import java.lang.management.ThreadInfo;
import java.util.logging.Logger;

public class ApolloBot {

    @Getter @Setter private static ApolloBot instance;

    @Getter private static Logger logger = Logger.getLogger("ApolloBot");

    private boolean ensureStart() {
        if(!FileUtils.directoryExists("secure")) {
            logger.severe("'secure' folder is missing, you must create it yourself!");
            return false;
        }

        if(!FileUtils.fileExists("secure/bot.token")) {
            logger.severe("'bot.token' file is missing inside 'secure' folder, you must create it yourself!");
            return false;
        }

        String botToken = FileUtils.readRaw("secure/bot.token");
        if(botToken == null) {
            logger.severe("Failed to read 'bot.token' file, make sure it's not empty!");
            return false;
        }

        return true;
    }

    // Instances

    @Getter private JDA jda;
    @Getter private DatastoreManager datastoreManager;
    @Getter private CommandManager commandManager;

    public void start() {
        if(!ensureStart()) return;

        logger.info("Bot is starting...");

        // Initializers
        this.datastoreManager = new DatastoreManager();
        this.commandManager = new CommandManager();


        String botToken = FileUtils.readRaw("secure/bot.token");
        this.jda = JDABuilder.createDefault(botToken)
                .addEventListeners(new CommandListener())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .build();


        try {
            this.jda.awaitReady();

            // Post-load
            this.datastoreManager.load();
            this.commandManager.load();

            logger.info("Bot is ready!");

            // Keep-run task
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(20L);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }

                    this.datastoreManager.save();
                }
            }).start();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
