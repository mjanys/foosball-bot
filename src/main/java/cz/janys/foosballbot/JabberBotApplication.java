package cz.janys.foosballbot;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Martin Janys
 */
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "cz.janys.foosballbot.config.**")
public class JabberBotApplication {

    private static final Logger log = Logger.getLogger(JabberBotApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JabberBotApplication.class, args);
    }
}
