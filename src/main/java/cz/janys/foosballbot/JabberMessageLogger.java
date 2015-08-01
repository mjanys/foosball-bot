package cz.janys.foosballbot;

import cz.janys.jabberbot.JabberMessage;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;
import java.lang.String;

/**
 * @author Martin Janys
 */
public class JabberMessageLogger {

    public static void log(Logger logger, JabberMessage message, String logMessage) {
        LogMF.info(logger,
                "Received a message from: {0} with payload: {1}.\n\t{2}",
                message.from(), message.getPayload(), logMessage);
    }
}
