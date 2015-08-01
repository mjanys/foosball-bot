package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Messages;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Martin Janys
 */
public class HelpHandlerTest extends AbstractHandlerTest {

    @Test
    public void testDoHandleMessage() throws Exception {
        dispatcher.handleMessage(message("h"));
        assertEquals(Messages.HELP, lastMessage().getPayload().toString());
    }
}