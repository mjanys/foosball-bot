package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Messages;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Martin Janys
 */
public class NoHandlerTest extends AbstractHandlerTest {

    @Test
    public void testDoHandleMessageNoGame() throws Exception {
        dispatcher.handleMessage(message("ee"));
        assertEquals(Messages.NOT_IN_GAME, lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessage() throws Exception {
        // f 1
        dispatcher.handleMessage(message("f", PLAYER1));
        assertTrue(outgoingMessages.size() == 1);
        assertEquals(Messages.format(Messages.FOOSBALL, 1, foosballSize), lastMessage().getPayload().toString());
        // f 2
        dispatcher.handleMessage(message("j", PLAYER2));
        assertTrue(outgoingMessages.size() == 2);
        assertEquals(Messages.format(Messages.FOOSBALL, 2, foosballSize), lastMessage().getPayload().toString());
        // n 3
        dispatcher.handleMessage(message("n", PLAYER3));
        assertEquals(Messages.NOT_IN_GAME, lastMessage().getPayload().toString());
        // f 4
        dispatcher.handleMessage(message("j", PLAYER4));
        assertTrue(outgoingMessages.size() == 6);
        assertTrue(lastMessage().getPayload().toString().startsWith(Messages.format(Messages.FOOSBALL_LETS_GO, "")));
    }

    @Test
    public void testDoHandleMessage2() throws Exception {
        // f 1
        dispatcher.handleMessage(message("f", PLAYER1));
        assertTrue(outgoingMessages.size() == 1);
        assertEquals(Messages.format(Messages.FOOSBALL, 1, foosballSize), lastMessage().getPayload().toString());
        // n 1
        dispatcher.handleMessage(message("n", PLAYER1));
        assertTrue(outgoingMessages.size() == 2);
        assertEquals(Messages.OK_LEAVE, lastMessage().getPayload().toString());
        // f 2
        dispatcher.handleMessage(message("j", PLAYER2));
        assertEquals(Messages.format(Messages.FOOSBALL, 1, foosballSize), lastMessage().getPayload().toString());
    }
}