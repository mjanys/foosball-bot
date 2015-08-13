package cz.janys.foosballbot.handlers;

import cz.janys.jabberbot.ConversationConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Martin Janys
 */
@Configuration
public class JabberbotMockConfig extends cz.janys.test.jabberbot.JabberbotMockConfig {

    @Value("${conversation.duration.sec}")
    private int delay;

    @Bean
    public ConversationConfiguration foosball() {
        ConversationConfiguration configuration = new ConversationConfiguration();
        configuration.setDelay(delay);
        return configuration;
    }
}
