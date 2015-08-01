package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.JabberMessageLogger;
import cz.janys.foosballbot.Messages;
import cz.janys.foosballbot.dto.JoinedPlayer;
import cz.janys.foosballbot.handlers.generic.AbstractFoosballHandler;
import cz.janys.foosballbot.util.MessageUtils;
import cz.janys.jabberbot.ConversationScope;
import cz.janys.jabberbot.JabberMessage;
import org.apache.log4j.Logger;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.xmpp.XmppHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cz.janys.foosballbot.Command.MESSAGE;

/**
 * @author Martin Janys
 */
@Component
public class MessageHandler extends AbstractFoosballHandler {

    private static Logger log = Logger.getLogger(MessageHandler.class);

    public MessageHandler() {
        super(MESSAGE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Message<?> doHandleMessage(JabberMessage message, ConversationScope<String, Object> conversationScope) throws MessagingException {
        JabberMessageLogger.log(log, message, "MESSAGE");
        String from = MessageUtils.parseJabber(message.from());

        if (message.getToken(1) != null) {
            Set<JoinedPlayer> playersInGame = MessageUtils.getFoosballPlayers(conversationScope);
            if (!CollectionUtils.isEmpty(playersInGame)) {
                if (!playersInGame.contains(new JoinedPlayer(from))) { // player not in hunting
                    return new GenericMessage<>(Messages.NOT_IN_GAME);
                }

                List<JoinedPlayer> playerStream = playersInGame.stream()
                        .filter(JoinedPlayer::isJabber)
                        .filter(p -> !p.getPlayer().equals(from))
                        .collect(Collectors.toList()); // not current
                String playerList = playerStream.stream()
                        .map(JoinedPlayer::getPlayer)
                        .collect(Collectors.joining("\n"));
                sendMessage(from, message.getToken(1), playerStream);
                return new GenericMessage<>(Messages.format(Messages.MESSAGE_SENT, playerList));
            }
            else {
                return new GenericMessage<>(Messages.NO_FOOSBALL_HUNTING);
            }
        }
        else {
            return new GenericMessage<>(Messages.WRONG_SYNTAX);
        }
    }

    private void sendMessage(String from, String message, List<JoinedPlayer> players) {
        players.stream()
                .forEach(player -> sendMessage(from, player.getPlayer(), message));
    }

    private boolean sendMessage(String from, String to, String message) {
        Message<?> response = MessageBuilder
                .withPayload(Messages.format(Messages.MESSAGE, from, message))
                .setHeader(XmppHeaders.TO, to)
                .setReplyChannel(outboundChannel)
                .build();
        return outboundChannel.send(response);
    }

}
