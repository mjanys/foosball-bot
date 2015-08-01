package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.JabberMessageLogger;
import cz.janys.foosballbot.Messages;
import cz.janys.foosballbot.dto.JoinedPlayer;
import cz.janys.foosballbot.handlers.generic.AbstractFoosballHandler;
import cz.janys.foosballbot.util.MessageUtils;
import cz.janys.jabberbot.ConversationScope;
import cz.janys.jabberbot.JabberMessage;
import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.Set;

import static cz.janys.foosballbot.Command.NO;

/**
 * @author Martin Janys
 */
@Component
public class NoHandler extends AbstractFoosballHandler {

    private static Logger log = Logger.getLogger(NoHandler.class);

    public NoHandler() {
        super(NO);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Message<?> doHandleMessage(JabberMessage message, ConversationScope<String, Object> conversationScope) throws MessagingException {
        JabberMessageLogger.log(log, message, "NO");

        Set<JoinedPlayer> players = MessageUtils.getFoosballPlayers(conversationScope);
        JoinedPlayer player = new JoinedPlayer(MessageUtils.parseJabber(message.from()));
        if (players != null && players.contains(player)) {
            players.remove(player);
            return new GenericMessage<>(Messages.OK_LEAVE);
        }
        else {
            return new GenericMessage<>(Messages.NOT_IN_GAME);
        }
    }
}
