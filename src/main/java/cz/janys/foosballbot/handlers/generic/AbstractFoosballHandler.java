package cz.janys.foosballbot.handlers.generic;

import cz.janys.foosballbot.Command;
import cz.janys.foosballbot.Constants;
import cz.janys.foosballbot.repository.PlayerRepository;
import cz.janys.jabberbot.JabberMessage;
import cz.janys.jabberbot.JabberMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageChannel;

/**
 * @author Martin Janys
 */
public abstract class AbstractFoosballHandler extends JabberMessageHandler {

    protected final Command command;
    @Value("${foosball.size}")
    protected Integer foosballSize;
    @Autowired
    protected PlayerRepository playerRepository;
    @Qualifier("outboundChannel")
    @Autowired
    protected MessageChannel outboundChannel;

    public AbstractFoosballHandler(Command command) {
        this.command = command;
    }

    @Override
    public boolean canHandle(JabberMessage jabberMessage) {
        return command.cmd().contains(jabberMessage.getToken(0));
    }

    @Override
    public String getConversationId() {
        return Constants.FOOSBALL_CONVERSATION_ID;
    }
}
