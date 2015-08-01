package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Constants;
import cz.janys.foosballbot.JabberMessageLogger;
import cz.janys.foosballbot.Messages;
import cz.janys.foosballbot.entity.PlayerEntity;
import cz.janys.foosballbot.handlers.generic.AbstractFoosballHandler;
import cz.janys.foosballbot.util.MessageUtils;
import cz.janys.jabberbot.ConversationScope;
import cz.janys.jabberbot.JabberMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.xmpp.XmppHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.List;

import static cz.janys.foosballbot.Command.FOOSBALL;

/**
 * @author Martin Janys
 */
@Component
public class FoosballHandler extends AbstractFoosballHandler {

    private static Logger log = Logger.getLogger(FoosballHandler.class);

    @Autowired
    private YesHandler yesHandler;

    public FoosballHandler() {
        super(FOOSBALL);
    }

    @Override
    public Message<?> doHandleMessage(JabberMessage message, ConversationScope<String, Object> conversationScope) throws MessagingException {
        JabberMessageLogger.log(log, message, "FOOSBALL");

        // foos in progress
        if (MessageUtils.isFoosballInProgress(conversationScope)) {
            return new GenericMessage<Object>(Messages.FOOSBALL_IN_PROGRESS);
        }

        if (!MessageUtils.isHuntingMessagesSent(conversationScope)) {
            wannaGame(MessageUtils.parseJabber(message.from()));
            conversationScope.put(Constants.FOOSBALL_HUNTING_MESSAGE_SENT, true);
        }

        // dispatch as yes to join current player
        return yesHandler.doHandleMessage(MessageUtils.createJabberMessage("j", message), conversationScope);
    }

    private void wannaGame(String from) {
        // send message to registered players
        List<PlayerEntity> allPlayers = playerRepository.findAll();
        allPlayers.stream()
                .filter(p -> !from.startsWith(p.getJabber()))
                .forEach(p -> sendMessageWannaGame(p.getJabber()));
    }


    private boolean sendMessageWannaGame(String player) {
        Message<?> response = MessageBuilder
                .withPayload(Messages.format(Messages.FOOSBALL_WANNA_GAME, ""))
                .setHeader(XmppHeaders.TO, player)
                .setReplyChannel(outboundChannel)
                .build();
        return outboundChannel.send(response);
    }
}
