package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Messages;

import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Martin Janys
 */
public abstract class ScenarioTest extends AbstractHandlerTest {

    protected void send(String message, String from) {
        dispatcher.handleMessage(message(message, from));
    }

    protected void assertThatGame() {
        if (!lastMessage().getPayload().toString().startsWith(Messages.format(Messages.FOOSBALL_LETS_GO, ""))) {
            fail("\n" +
                    outgoingMessages.stream()
                            .map(message -> message.getPayload().toString())
                            .collect(Collectors.joining(",\n"))
            );
        }
    }
    protected void assertThatGameRunning() {
        if (!lastMessage().getPayload().toString().startsWith(Messages.format(Messages.FOOSBALL_IN_PROGRESS, ""))) {
            fail("\n" +
                    outgoingMessages.stream()
                            .map(message -> message.getPayload().toString())
                            .collect(Collectors.joining(",\n"))
            );
        }
    }
}