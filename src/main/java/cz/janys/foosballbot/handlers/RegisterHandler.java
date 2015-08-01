package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.JabberMessageLogger;
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

import static cz.janys.foosballbot.Command.REGISTER;

/**
 * @author Martin Janys
 */
@Component
public class RegisterHandler extends AbstractFoosballHandler {

    private static Logger log = Logger.getLogger(RegisterHandler.class);

    public RegisterHandler() {
        super(REGISTER);
    }

    @Override
    public Message<?> doHandleMessage(JabberMessage message, ConversationScope<String, Object> conversationScope) throws MessagingException {
        JabberMessageLogger.log(log, message, "FOOSBALL");
        String playerJabber = MessageUtils.parseJabber(message.from());
        if (save(playerJabber)) {
            return new GenericMessage<>(Messages.format(Messages.REGISTERED, playerJabber));
        }
        else {
            return new GenericMessage<>(Messages.format(Messages.ALREADY_REGISTERED));
        }
    }

    private boolean save(String playerJabber) {
        PlayerEntity byJabber = playerRepository.findByJabber(playerJabber);
        if (byJabber == null) {
            PlayerEntity player = new PlayerEntity();
            player.setJabber(playerJabber   );
            playerRepository.save(player);
            return true;
        }
        else {
            return false;
        }
    }
}
