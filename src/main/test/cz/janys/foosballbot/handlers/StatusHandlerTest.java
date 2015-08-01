package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.Messages;
import cz.janys.foosballbot.dto.JoinedPlayer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Martin Janys
 */
public class StatusHandlerTest extends AbstractHandlerTest {

    @Test
    public void testDoHandleMessageNoPlayers() throws Exception {
        dispatcher.handleMessage(message("s"));
        assertEquals(Messages.NO_PLAYERS, lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessagePlayers() throws Exception {

        String playerList = Arrays.asList(PLAYER1, PLAYER2).stream()
                .sorted()
                .collect(Collectors.joining("\n"));

        dispatcher.handleMessage(message("s"));
        assertEquals(Messages.NO_PLAYERS, lastMessage().getPayload().toString());

        dispatcher.handleMessage(message("f", PLAYER1));
        dispatcher.handleMessage(message("j", PLAYER2));

        dispatcher.handleMessage(message("s"));
        assertEquals(Messages.format(Messages.PLAYERS, 2, foosballSize, playerList), lastMessage().getPayload().toString());
    }

    @Test
    public void testDoHandleMessagePlayersWithLeav() throws Exception {

        String playerList = Arrays.asList(PLAYER1, PLAYER2).stream()
                .sorted()
                .collect(Collectors.joining("\n"));

        dispatcher.handleMessage(message("s"));
        assertEquals(Messages.NO_PLAYERS, lastMessage().getPayload().toString());

        dispatcher.handleMessage(message("f", PLAYER1));
        dispatcher.handleMessage(message("j", PLAYER2));

        dispatcher.handleMessage(message("s"));
        assertEquals(Messages.format(Messages.PLAYERS, 2, foosballSize, playerList), lastMessage().getPayload().toString());

        dispatcher.handleMessage(message("leave", PLAYER2));
        playerList = Collections.singletonList(PLAYER1).stream()
                .sorted()
                .collect(Collectors.joining("\n"));

        dispatcher.handleMessage(message("s"));
        assertEquals(Messages.format(Messages.PLAYERS, 1, foosballSize, playerList), lastMessage().getPayload().toString());

    }
}