package cz.janys.foosballbot.config;

import cz.janys.jabberbot.ConversationConfiguration;
import cz.janys.jabberbot.config.JabberbotBaseConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

/**
 * @author Martin Janys
 */
@Configuration
@PropertySources({
        @PropertySource(value = "classpath:jabberbot.properties"),
        @PropertySource(value = "classpath:foosball.properties")
})
public class JabberbotConfig extends JabberbotBaseConfig {

    @Value("${conversation.duration.sec}")
    private int delay;

    @Bean
    public ConversationConfiguration foosball() {
        ConversationConfiguration configuration = new ConversationConfiguration();
        configuration.setDelay(delay);
        return configuration;
    }
}
