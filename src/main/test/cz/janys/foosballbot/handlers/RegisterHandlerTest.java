package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Messages;
import cz.janys.foosballbot.repository.PlayerRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author Martin Janys
 */
public class RegisterHandlerTest extends AbstractHandlerTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testDoHandleMessage() throws Exception {
        // reg
        dispatcher.handleMessage(message("reg"));
        assertTrue(outgoingMessages.size() == 1);
        assertEquals(outgoingMessages.get(0).getPayload().toString(), Messages.format(Messages.REGISTERED, TEST_JABBER));
        assertTrue(playerRepository.count() == 1);
        assertNotNull(playerRepository.findByJabber("test"));
    }

    @Test
    public void testDoHandleMessageReregistration() throws Exception {
        // reg
        dispatcher.handleMessage(message("reg"));
        assertTrue(outgoingMessages.size() == 1);
        assertEquals(outgoingMessages.get(0).getPayload().toString(), Messages.format(Messages.REGISTERED, TEST_JABBER));
        assertTrue(playerRepository.count() == 1);
        assertNotNull(playerRepository.findByJabber("test"));

        // reg
        dispatcher.handleMessage(message("reg"));
        assertTrue(outgoingMessages.size() == 2);
        assertEquals(outgoingMessages.get(1).getPayload().toString(), Messages.ALREADY_REGISTERED);
        assertTrue(playerRepository.count() == 1);
        assertNotNull(playerRepository.findByJabber("test"));
    }

    @Test
    public void testDoHandleMessage2() throws Exception {
        // reg 1
        dispatcher.handleMessage(message("reg", PLAYER1));
        assertTrue(outgoingMessages.size() == 1);
        assertEquals(Messages.format(Messages.REGISTERED, PLAYER1), lastMessage().getPayload().toString());
        assertTrue(playerRepository.count() == 1);
        assertNotNull(playerRepository.findByJabber(PLAYER1));
        // reg 2
        dispatcher.handleMessage(message("register", PLAYER2));
        assertTrue(outgoingMessages.size() == 2);
        assertEquals(Messages.format(Messages.REGISTERED, PLAYER2), lastMessage().getPayload().toString());
        assertTrue(playerRepository.count() == 2);
        assertNotNull(playerRepository.findByJabber(PLAYER2));

        // unreg1
        dispatcher.handleMessage(message("unreg", PLAYER1));
        assertTrue(outgoingMessages.size() == 3);
        assertEquals(Messages.format(Messages.UNREGISTERED, PLAYER1), lastMessage().getPayload().toString());
        assertTrue(playerRepository.count() == 1);
        assertNull(playerRepository.findByJabber(PLAYER1));

        // unreg2
        dispatcher.handleMessage(message("unregister", PLAYER2));
        assertTrue(outgoingMessages.size() == 4);
        assertEquals(Messages.format(Messages.UNREGISTERED, PLAYER2), lastMessage().getPayload().toString());
        assertTrue(playerRepository.count() == 0);
        assertNull(playerRepository.findByJabber(PLAYER2));

        // unreg2
        dispatcher.handleMessage(message("unregister", PLAYER2));
        assertTrue(outgoingMessages.size() == 5);
        assertEquals(Messages.NO_REGISTERED, lastMessage().getPayload().toString());
        assertTrue(playerRepository.count() == 0);
        assertNull(playerRepository.findByJabber(PLAYER1));
        assertNull(playerRepository.findByJabber(PLAYER2));
    }

}