package cz.janys.foosballbot;

import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * @author Martin Janys
 */
public class Messages {

    private static final Logger log = Logger.getLogger(Messages.class);

    public static final String HELP = "Help:\n" +
            "https://github.com/mjanys/foosball-bot\n" +
            "REGISTER(\"reg\", \"register\") - register to player list\n" +
            "UNREGISTER(\"unreg\", \"unregister\") - unregister from player list\n" +
            "FOOSBALL(\"f\") - start hunting for players\n" +
            "YES(\"y\", \"yes\", \"jj\", \"j\") - yes\n" +
            "NO(\"n\", \"no\", \"nn\", \"leave\", \"ee\", \"e\") - no\n" +
            "STATUS(\"s\", \"status\") - joined players\n" +
            "PLUS(\"+\") - join player \"+ name\"\n" +
            "HELP(\"h\", \"help\") - help\n" +
            "MESSAGE(\"m\", \"message\") - message to joined players\n";

    public static final String REGISTERED = "You are registered as %s";
    public static final String ALREADY_REGISTERED = "You are already registered";

    public static final String UNREGISTERED = "You are unregistered - %s";
    public static final String NO_REGISTERED = "You are not registered";

    public static final String FOOSBALL = "You are joined to game. Foosball players (%s/%s)";
    public static final String FOOSBALL_ALREADY_IN = "You are already in game (%s/%s)";
    public static final String FOOSBALL_LETS_GO = "Game is complete! Let's GO!\nPlayers:%s";
    public static final String FOOSBALL_IN_PROGRESS = "Game is already running :(";
    public static final String FOOSBALL_WANNA_GAME = "Wanna foosball? %s";
    public static final String NO_FOOSBALL_HUNTING = "No foosball hunting";
    public static final String LAST_PLAYER_MISSING = "Last player WANTED!";

    public static final String OK_LEAVE = "Okay :(, come back soon";

    public static final String NO_PLAYERS = "No players";
    public static final String PLAYERS = "Foosball players (%s/%s)\n%s";
    public static final String WRONG_SYNTAX = "Wrong syntax";
    public static final String MESSAGE_SENT = "Message was sent to:\n%s";
    public static final String MESSAGE = "From %s: %s";
    public static final String NOT_IN_GAME = "You are not in game";

    public static String format(String message, Object ... args) {
        LogMF.debug(log, "{0} {1}", message, Arrays.toString(args));
        return String.format(message, args);
    }

}
