package cz.janys.foosballbot.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;


/**
 * @author Martin Janys
 */
@Configuration
@PropertySource({"classpath:/database-settings.properties"})
public class DatabaseConfig extends ConfigurableConfig {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(prop("db.jdbc.driverClassName"));
        dataSource.setUrl(prop("db.jdbc.url"));
        dataSource.setUsername(prop("db.jdbc.username"));
        dataSource.setPassword(prop("db.jdbc.password"));
        return dataSource;
    }

}
