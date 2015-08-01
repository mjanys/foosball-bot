package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Command;
import cz.janys.foosballbot.Constants;
import cz.janys.foosballbot.JabberMessageLogger;
import cz.janys.foosballbot.Messages;
import cz.janys.foosballbot.dto.JoinedPlayer;
import cz.janys.foosballbot.entity.PlayerEntity;
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

import java.util.Set;
import java.util.stream.Collectors;

import static cz.janys.foosballbot.Command.YES;

/**
 * @author Martin Janys
 */
@Component
public class YesHandler extends AbstractFoosballHandler {

    private static Logger log = Logger.getLogger(YesHandler.class);

    YesHandler(Command command) {
        super(command);
    }

    public YesHandler() {
        super(YES);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Message<?> doHandleMessage(JabberMessage message, ConversationScope<String, Object> conversationScope) throws MessagingException {
        JabberMessageLogger.log(log, message, "YES");

        // foos in progress
        if (MessageUtils.isFoosballInProgress(conversationScope)) {
            return new GenericMessage<Object>(Messages.FOOSBALL_IN_PROGRESS);
        }
        // foos hunting not start
        if (!MessageUtils.isHuntingMessagesSent(conversationScope)) {
            return new GenericMessage<Object>(Messages.NO_FOOSBALL_HUNTING);
        }

        conversationScope.refresh();

        String playerJabber = MessageUtils.parseJabber(message.from());
        Set<JoinedPlayer> players = MessageUtils.getFoosballPlayers(conversationScope);

        if (!players.contains(new JoinedPlayer(playerJabber))) {
            if (!foosball(playerJabber, players)) {// not complete foos
                return new GenericMessage<>(Messages.format(Messages.FOOSBALL, players.size(), foosballSize));
            }
            else {
                letsGo(players);
                conversationScope.put(Constants.FOOSBALL_IN_PROGRESS, true);
                return null; // already send message about complete foot
            }
        }
        else {
            return new GenericMessage<>(Messages.format(Messages.FOOSBALL_ALREADY_IN, players.size(), foosballSize));
        }
    }

    private boolean foosball(String playerJabber, Set<JoinedPlayer> playersInGame) {
        playersInGame.add(new JoinedPlayer(playerJabber, command.equals(YES)));

        if (playersInGame.size() == foosballSize) {
            return true; // let's go
        }
        if (playersInGame.size() == foosballSize - 1) {
            // one player missing
            onePlayerMissing(playersInGame);
        }

        return false; // not yet
    }

    private void onePlayerMissing(Set<JoinedPlayer> playersInGame) {
        Set<String> playerNames = playersInGame
                .stream()
                .map(JoinedPlayer::getPlayer)
                .collect(Collectors.toSet());
        Set<String> missingPlayers = playerRepository.findAll()
                .stream()
                .map(PlayerEntity::getJabber)
                .filter(p -> !playerNames.contains(p))
                .collect(Collectors.toSet());
        missingLastPlayer(missingPlayers);
    }

    private void letsGo(Set<JoinedPlayer> playersInGame) {
        // send message to player in game
        String playerList = playersInGame.stream()
                .map(JoinedPlayer::getPlayer)
                .collect(Collectors.joining("\n"));
        playersInGame.stream()
                .filter(JoinedPlayer::isJabber)
                .forEach(p -> sendMessageLetsGo(p, playerList));
        playersInGame.clear();
    }

    private boolean sendMessageLetsGo(JoinedPlayer player, String playerList) {
        if (player.isJabber()) {
            Message<?> response = MessageBuilder
                    .withPayload(Messages.format(Messages.FOOSBALL_LETS_GO, playerList))
                    .setHeader(XmppHeaders.TO, player)
                    .setReplyChannel(outboundChannel)
                    .build();
            return outboundChannel.send(response);
        }
        return true;// it is ok (non jabber player)
    }

    private void missingLastPlayer(Set<String> missingPlayers) {
        missingPlayers.forEach(this::sendMissingLastPlayerMessage);
    }

    private boolean sendMissingLastPlayerMessage(String missingPlayer) {
        Message<?> response = MessageBuilder
                .withPayload(Messages.LAST_PLAYER_MISSING)
                .setHeader(XmppHeaders.TO, missingPlayer)
                .setReplyChannel(outboundChannel)
                .build();
        return outboundChannel.send(response);
    }

}
