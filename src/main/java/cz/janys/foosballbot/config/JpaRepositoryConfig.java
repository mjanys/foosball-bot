package cz.janys.foosballbot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Martin Janys
 */
@Configuration
@EnableJpaRepositories(
        basePackages = {"cz.janys.foosballbot.repository.**"}
)
public class JpaRepositoryConfig extends ConfigurableConfig {
}
