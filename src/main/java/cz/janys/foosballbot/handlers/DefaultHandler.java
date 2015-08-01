package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Constants;
import cz.janys.foosballbot.JabberMessageLogger;
import cz.janys.jabberbot.ConversationScope;
import cz.janys.jabberbot.DefaultJabberMessageHandler;
import cz.janys.jabberbot.JabberMessage;
import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * @author Martin Janys
 */
@Component
public class DefaultHandler extends DefaultJabberMessageHandler {

    private Logger log = Logger.getLogger(DefaultHandler.class);

    @Override
    public String getConversationId() {
        return Constants.FOOSBALL_CONVERSATION_ID;
    }

    @Override
    public boolean canHandle(JabberMessage message) {
        return true;
    }

    @Override
    public Message<?> doHandleMessage(JabberMessage message, ConversationScope<String, Object> conversationScope) throws MessagingException {
        JabberMessageLogger.log(log, message, "");
        return new GenericMessage<Object>("Unknown cmd: " + message.getPayload());
    }
}
