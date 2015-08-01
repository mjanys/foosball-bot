package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Constants;
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
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static cz.janys.foosballbot.Command.STATUS;

/**
 * @author Martin Janys
 */
@Component
public class StatusHandler extends AbstractFoosballHandler {

    private static Logger log = Logger.getLogger(StatusHandler.class);

    public StatusHandler() {
        super(STATUS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Message<?> doHandleMessage(JabberMessage message, ConversationScope<String, Object> conversationScope) throws MessagingException {
        JabberMessageLogger.log(log, message, "STATUS");

        Set<JoinedPlayer> players = MessageUtils.getFoosballPlayers(conversationScope);
        if (CollectionUtils.isEmpty(players)) {
            return new GenericMessage<>(Messages.NO_PLAYERS);
        }
        else {
            String playerList = players.stream()
                    .map(JoinedPlayer::getPlayer)
                    .sorted()
                    .collect(Collectors.joining("\n"));
            return new GenericMessage<>(Messages.format(Messages.PLAYERS, players.size(), foosballSize, playerList));
        }
    }
}
