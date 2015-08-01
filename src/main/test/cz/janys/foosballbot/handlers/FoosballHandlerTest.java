package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Messages;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.*;

/**
 * @author Martin Janys
 */
public class FoosballHandlerTest extends AbstractHandlerTest {

    @Test
    public void testDoHandleMessage() throws Exception {
        // f
        dispatcher.handleMessage(message("f", PLAYER1));
        assertTrue(outgoingMessages.size() == 1);
        assertEquals(Messages.format(Messages.FOOSBALL, 1, foosballSize), lastMessage().getPayload().toString());
        // f
        dispatcher.handleMessage(message("f", PLAYER1));
        assertTrue(outgoingMessages.size() == 2);
        assertEquals(Messages.format(Messages.FOOSBALL_ALREADY_IN, 1, foosballSize), lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessage2() throws Exception {
        // f
        dispatcher.handleMessage(message("f", PLAYER1));
        assertTrue(outgoingMessages.size() == 1);
        assertEquals(Messages.format(Messages.FOOSBALL, 1, foosballSize), lastMessage().getPayload().toString());
        // f
        dispatcher.handleMessage(message("j", PLAYER1));
        assertTrue(outgoingMessages.size() == 2);
        assertEquals(Messages.format(Messages.FOOSBALL_ALREADY_IN, 1, foosballSize), lastMessage().getPayload().toString());
    }
}