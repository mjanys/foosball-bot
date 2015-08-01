package cz.janys.foosballbot.util;

import cz.janys.foosballbot.Constants;
import cz.janys.foosballbot.dto.JoinedPlayer;
import cz.janys.jabberbot.ConversationScope;
import cz.janys.jabberbot.JabberMessage;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

import java.util.*;

/**
 * @author Martin Janys
 */
public class MessageUtils {

    public static GenericMessage<?> createMessage(String m, String from) {
        return new GenericMessage<>(m, new MessageHeaders(new HashMap<String, Object>(){{
            put("xmpp_from", from);
        }}));
    }

    public static JabberMessage createJabberMessage(String m, String from) {
        return new JabberMessage(createMessage(m, from));
    }

    public static JabberMessage createJabberMessage(String m, JabberMessage jabberMessage) {
        return createJabberMessage(m, jabberMessage.from());
    }

    public static String parseJabber(String jabber) {
        return jabber.split("/")[0];
    }

    @SuppressWarnings("unchecked")
    public static Set<JoinedPlayer> getFoosballPlayers(ConversationScope<String, Object> conversationScope) {
        return (Set<JoinedPlayer>) conversationScope.get(Constants.FOOSBALL_PLAYERS, new HashSet<JoinedPlayer>());
    }

    @SuppressWarnings("unchecked")
    public static Set<JoinedPlayer> getFoosballPlayers(Map<String, Object> conversationScope) {
        return (Set<JoinedPlayer>) conversationScope.getOrDefault(Constants.FOOSBALL_PLAYERS, new HashSet<JoinedPlayer>());
    }

    public static boolean isFoosballInProgress(Map<String, Object> conversationScope) {
        return Optional
                .ofNullable((Boolean) conversationScope.get(Constants.FOOSBALL_IN_PROGRESS))
                .orElse(false);
    }

    public static boolean isHuntingMessagesSent(Map<String, Object> conversationScope) {
        return Optional
                .ofNullable((Boolean) conversationScope.get(Constants.FOOSBALL_HUNTING_MESSAGE_SENT))
                .orElse(false);
    }

}
