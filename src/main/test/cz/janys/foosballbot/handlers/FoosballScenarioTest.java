package cz.janys.foosballbot.handlers;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

/**
 * @author Martin Janys
 */
public class FoosballScenarioTest extends ScenarioTest {

    @Test
    public void scenarioUnknown() {
        send("test", PLAYER1);
        Assert.assertTrue(lastMessage().getPayload().toString().startsWith("Unknown cmd:"));
    }

    @Test
    public void scenario1() {
        send("f", PLAYER1);
        send("jj", PLAYER2);
        send("j", PLAYER3);
        assertThatGame();
    }

    @Test
    public void scenario2() {
        send("f", PLAYER1);
        send("jj", PLAYER2);
        send("leave", PLAYER1);
        send("j", PLAYER3);
        send("j", PLAYER4);
        assertThatGame();
    }

    @Test
    public void scenario3() {
        send("f", PLAYER1);
        send("jj", PLAYER2);
        send("ee", PLAYER2);
        send("j", PLAYER3);
        send("j", PLAYER4);
        assertThatGame();
    }

    @Test
    public void scenario4() {
        send("f", PLAYER1);
        send("+ 1", PLAYER1);
        send("+ 2", PLAYER1);
        assertThatGame();
    }

    @Test
    public void scenario5() {
        send("f", PLAYER1);
        send("+ 1", PLAYER1);
        send("+ 1", PLAYER1);
        send("+ 2", PLAYER3);
        assertThatGame();
    }

    @Test
    public void scenario6() {
        send("f", PLAYER1);
        send("+ 1", PLAYER1);
        send("jj", PLAYER3);
        assertThatGame();
    }

    // GAME RUNNING
    @Test
    public void scenario7() {
        send("f", PLAYER1);
        send("jj", PLAYER2);
        send("jj", PLAYER3);
        send("f", PLAYER4);
        assertThatGameRunning();
    }

    @Test
    public void scenario8() {
        send("f", PLAYER1);
        send("jj", PLAYER2);
        send("jj", PLAYER3);
        send("f", PLAYER4);
        assertThatGameRunning();
        Map conversations = (Map) ReflectionTestUtils.getField(dispatcher, "conversations");
        conversations.clear();
        send("f", PLAYER1);
        send("jj", PLAYER2);
        send("jj", PLAYER3);
        assertThatGame();
    }

}