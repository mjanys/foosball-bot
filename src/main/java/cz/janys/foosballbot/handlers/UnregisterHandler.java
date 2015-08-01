package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Messages;
import cz.janys.foosballbot.entity.PlayerEntity;
import cz.janys.foosballbot.handlers.generic.AbstractFoosballHandler;
import cz.janys.foosballbot.util.MessageUtils;
import cz.janys.jabberbot.ConversationScope;
import cz.janys.jabberbot.JabberMessage;
import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import static cz.janys.foosballbot.Command.UNREGISTER;

/**
 * @author Martin Janys
 */
@Component
public class UnregisterHandler extends AbstractFoosballHandler {

    private static Logger log = Logger.getLogger(UnregisterHandler.class);

    public UnregisterHandler() {
        super(UNREGISTER);
    }

    @Override
    public Message<?> doHandleMessage(JabberMessage message, ConversationScope<String, Object> conversationScope) throws MessagingException {
        String playerJabber = MessageUtils.parseJabber(message.from());
        if (delete(playerJabber)) {
            return new GenericMessage<>(Messages.format(Messages.UNREGISTERED, playerJabber));
        }
        else {
            return new GenericMessage<>(Messages.format(Messages.NO_REGISTERED));
        }
    }

    private boolean delete(String playerJabber) {
        PlayerEntity byJabber = playerRepository.findByJabber(playerJabber);
        if (byJabber != null) {
            playerRepository.delete(byJabber);
            return true;
        }
        else {
            return false;
        }
    }
}
