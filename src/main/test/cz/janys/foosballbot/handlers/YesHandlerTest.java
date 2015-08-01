package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Messages;
import cz.janys.foosballbot.entity.PlayerEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Martin Janys
 */
public class YesHandlerTest extends AbstractHandlerTest {

    public void setUpPlayers() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setJabber("jabber");
        playerRepository.save(playerEntity);
        playerEntity = new PlayerEntity();
        playerEntity.setJabber("jabber2");
        playerRepository.save(playerEntity);
    }

    @After
    public void tearDown() {
        playerRepository.deleteAll();
    }

    @Test
    public void testDoHandleMessageNoGame() throws Exception {
        // j
        dispatcher.handleMessage(message("j", PLAYER1));
        assertTrue(outgoingMessages.size() == 1);
        assertEquals(Messages.NO_FOOSBALL_HUNTING, lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessage() throws Exception {
        // f
        dispatcher.handleMessage(message("f", PLAYER1));
        assertTrue(outgoingMessages.size() == 1);
        assertEquals(Messages.format(Messages.FOOSBALL, 1, foosballSize), lastMessage().getPayload().toString());
        // f
        dispatcher.handleMessage(message("f", PLAYER2));
        assertTrue(outgoingMessages.size() == 2);
        assertEquals(Messages.format(Messages.FOOSBALL, 2, foosballSize), lastMessage().getPayload().toString());
        // f
        dispatcher.handleMessage(message("f", PLAYER3));
        assertTrue(outgoingMessages.size() == 5);
    }

    @Test
    public void testDoHandleMessageYes() throws Exception {
        // f
        dispatcher.handleMessage(message("f", PLAYER1));
        assertTrue(outgoingMessages.size() == 1);
        assertEquals(Messages.format(Messages.FOOSBALL, 1, foosballSize), lastMessage().getPayload().toString());
        // f
        dispatcher.handleMessage(message("j", PLAYER2));
        assertTrue(outgoingMessages.size() == 2);
        assertEquals(Messages.format(Messages.FOOSBALL, 2, foosballSize), lastMessage().getPayload().toString());
        // f
        dispatcher.handleMessage(message("j", PLAYER3));
        assertTrue(outgoingMessages.size() == 5);
    }

    @Test
    public void testDoHandleMessageAlreadyRunning1() throws Exception {
        testDoHandleMessageYes();
        dispatcher.handleMessage(message("f", PLAYER4));
        assertEquals(Messages.FOOSBALL_IN_PROGRESS, lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessageAlreadyRunning2() throws Exception {
        testDoHandleMessageYes();
        dispatcher.handleMessage(message("j", PLAYER4));
        assertEquals(Messages.FOOSBALL_IN_PROGRESS, lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessageLastPlayer() throws Exception {
        setUpPlayers();
        dispatcher.handleMessage(message("f", PLAYER1));
        dispatcher.handleMessage(message("j", PLAYER2));
        assertEquals(2, outgoingMessages
                .stream()
                .map(message -> message.getPayload().toString())
                .filter(m -> m.equals(Messages.LAST_PLAYER_MISSING))
                .count());
    }
}