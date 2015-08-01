package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Messages;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Martin Janys
 */
public class PlusHandlerTest extends AbstractHandlerTest {

    @Test
    public void testDoHandleMessageWrong() throws Exception {
        dispatcher.handleMessage(message("+"));
        assertEquals(Messages.WRONG_SYNTAX, lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessageNoGame() throws Exception {
        dispatcher.handleMessage(message("+ X"));
        assertEquals(Messages.NO_FOOSBALL_HUNTING, lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessageAlreadyInGame() throws Exception {
        dispatcher.handleMessage(message("f"));
        dispatcher.handleMessage(message("+ X"));
        dispatcher.handleMessage(message("+ X"));
        assertEquals(Messages.format(Messages.FOOSBALL_ALREADY_IN, 2, foosballSize), lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessage() throws Exception {
        dispatcher.handleMessage(message("f"));
        dispatcher.handleMessage(message("+ X"));
        dispatcher.handleMessage(message("+ Y"));
        assertEquals(3, outgoingMessages.size());
        assertTrue(lastMessage().getPayload().toString().startsWith(Messages.format(Messages.FOOSBALL_LETS_GO, "")));
    }
}