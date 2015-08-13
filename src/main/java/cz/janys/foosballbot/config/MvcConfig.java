package cz.janys.foosballbot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Martin Janys
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "cz.janys.foosballbot.mvc.**")
public class MvcConfig {
}
