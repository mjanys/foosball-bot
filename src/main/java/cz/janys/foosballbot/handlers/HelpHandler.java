package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.JabberMessageLogger;
import cz.janys.foosballbot.Messages;
import cz.janys.foosballbot.handlers.generic.AbstractFoosballHandler;
import cz.janys.jabberbot.ConversationScope;
import cz.janys.jabberbot.JabberMessage;
import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import static cz.janys.foosballbot.Command.HELP;

/**
 * @author Martin Janys
 */
@Component
public class HelpHandler extends AbstractFoosballHandler {

    private static Logger log = Logger.getLogger(HelpHandler.class);

    public HelpHandler() {
        super(HELP);
    }

    @Override
    public Message<?> doHandleMessage(JabberMessage message, ConversationScope<String, Object> conversationScope) throws MessagingException {
        JabberMessageLogger.log(log, message, "HELP");
        return new GenericMessage<>(Messages.HELP);
    }
}
