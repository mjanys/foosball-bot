package cz.janys.foosballbot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Martin Janys
 */
@Configuration
@ComponentScan(basePackages = "cz.janys.foosballbot.handlers.**")
public class HandlersConfig {
}
