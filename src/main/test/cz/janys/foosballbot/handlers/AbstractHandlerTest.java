package cz.janys.foosballbot.handlers;

import cz.janys.foosballbot.config.*;
import cz.janys.foosballbot.repository.PlayerRepository;
import cz.janys.foosballbot.util.MessageUtils;
import cz.janys.jabberbot.ConversationScope;
import cz.janys.jabberbot.JabberMessageDispatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;

/**
 * @author Martin Janys
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {
        "classpath:/database-test-settings.properties",
        "classpath:/foosball-test.properties"
})
@ContextConfiguration(classes = {
        JabberbotMockConfig.class,
        HandlersConfig.class,
        DatabaseConfig.class,
        JpaConfig.class,
        JpaRepositoryConfig.class
})
public abstract class AbstractHandlerTest {

    protected static String TEST_JABBER = "test";
    protected static String PLAYER1 = "player1";
    protected static String PLAYER2 = "player2";
    protected static String PLAYER3 = "player3";
    protected static String PLAYER4 = "player4";

    @Value("${foosball.size}")
    protected Integer foosballSize;

    @Autowired
    protected PlayerRepository playerRepository;
    @Qualifier("outboundChannel")
    @Autowired
    protected MessageChannel outboundChannel;
    @Autowired
    protected JabberMessageDispatcher dispatcher;

    protected ConversationScope<String, Object> conversation;
    protected List<Message<?>> outgoingMessages;

    public AbstractHandlerTest() {
        this.conversation = ConversationScope.create();
    }

    @Before
    public void superSetUp() {
        assertTrue(CollectionUtils.isEmpty(playerRepository.findAll()));
        outgoingMessages = new ArrayList<>();
        ((Map) ReflectionTestUtils.getField(dispatcher, "conversations")).clear();
        initMocks();
    }

    @After
    public void superTearDown() {
        playerRepository.deleteAll();
    }

    protected void initMocks() {
        doAnswer(invocationOnMock -> {
            Message<?> m = (Message<?>) invocationOnMock.getArguments()[0];
            outgoingMessages.add(m);
            return true;
        }).when(outboundChannel).send(any(Message.class));
    }

    protected Message<?> lastMessage() {
        return outgoingMessages.get(outgoingMessages.size() - 1);
    }

    protected GenericMessage message(String m) {
        return message(m, TEST_JABBER);
    }

    protected GenericMessage message(String m, String from) {
        return MessageUtils.createMessage(m, from);
    }

}