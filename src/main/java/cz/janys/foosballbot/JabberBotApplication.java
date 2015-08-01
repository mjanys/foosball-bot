package cz.janys.foosballbot;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Martin Janys
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
@ImportResource(
        value = {
                "classpath:spring-context/admin-context.xml",
                "classpath:spring-context/jpa-config.xml",
                "classpath:spring-context/jabberbot-spring-context-ext.xml",
                "classpath:jabberbot-spring-context.xml"
        },
        reader = XmlBeanDefinitionReader.class
)
public class JabberBotApplication {

    private static final Logger log = Logger.getLogger(JabberBotApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JabberBotApplication.class, args);
    }
}
