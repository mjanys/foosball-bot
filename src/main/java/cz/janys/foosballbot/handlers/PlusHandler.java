package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.JabberMessageLogger;
import cz.janys.foosballbot.Messages;
import cz.janys.foosballbot.util.MessageUtils;
import cz.janys.jabberbot.ConversationScope;
import cz.janys.jabberbot.JabberMessage;
import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import static cz.janys.foosballbot.Command.PLUS;

/**
 * @author Martin Janys
 */
@Component
public class PlusHandler extends YesHandler {

    private static Logger log = Logger.getLogger(PlusHandler.class);

    public PlusHandler() {
        super(PLUS);
    }

    @Override
    public Message<?> doHandleMessage(JabberMessage message, ConversationScope<String, Object> conversationScope) throws MessagingException {
        JabberMessageLogger.log(log, message, "PLUS");
        if (message.getToken(1) != null) {
            JabberMessage jabberMessage = MessageUtils.createJabberMessage(message.getPayload(), message.getToken(1));
            return super.doHandleMessage(jabberMessage, conversationScope);
        }
        else {
            return new GenericMessage<>(Messages.WRONG_SYNTAX);
        }
    }
}
