package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Messages;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Martin Janys
 */
public class MessageHandlerTest extends AbstractHandlerTest {

    @Test
    public void testDoHandleMessageWrong() throws Exception {
        dispatcher.handleMessage(message("m", PLAYER1));
        assertEquals(Messages.WRONG_SYNTAX, lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessageNoGame() throws Exception {
        dispatcher.handleMessage(message("m abc", PLAYER1));
        assertEquals(Messages.NO_FOOSBALL_HUNTING, lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessageNoInGame() throws Exception {
        dispatcher.handleMessage(message("f", PLAYER1));
        dispatcher.handleMessage(message("m abc", PLAYER2));
        assertEquals(Messages.NOT_IN_GAME, lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessage1() throws Exception {
        dispatcher.handleMessage(message("f", PLAYER1));
        dispatcher.handleMessage(message("+ " + PLAYER2, PLAYER1));
        dispatcher.handleMessage(message("m 123", PLAYER1));
        assertEquals(3, outgoingMessages.size());
        assertEquals(Messages.format(Messages.MESSAGE_SENT, ""), lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessage2() throws Exception {
        dispatcher.handleMessage(message("f", PLAYER1));
        dispatcher.handleMessage(message("j", PLAYER2));
        dispatcher.handleMessage(message("m 123", PLAYER1));
        assertEquals(4, outgoingMessages.size());
        assertEquals(Messages.format(Messages.MESSAGE, PLAYER1, "123"), outgoingMessages.get(2).getPayload().toString());
        assertEquals(Messages.format(Messages.MESSAGE_SENT, PLAYER2), outgoingMessages.get(3).getPayload().toString());
    }
}