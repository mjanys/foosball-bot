package cz.janys.foosballbot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * @author Martin Janys
 */
public abstract class ConfigurableConfig {

    @Autowired
    public Environment environment;

    public String prop(String key) {
        return environment.getProperty(key);
    }
}
