package cz.janys.foosballbot.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Janys
 */
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:/database-settings.properties"})
public class JpaConfig extends ConfigurableConfig {

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emFactory);
        transactionManager.setJpaDialect(jpaDialect());
        return transactionManager;
    }

    @Bean
    public JpaDialect jpaDialect() {
        return new HibernateJpaDialect();
    }

    @Bean
    public FactoryBean<EntityManagerFactory> entityManagerFactory(DataSource dataSource, PersistenceProvider persistenceProvider) {
        LocalContainerEntityManagerFactoryBean emFactoryBean = new LocalContainerEntityManagerFactoryBean();
        emFactoryBean.setDataSource(dataSource);
        emFactoryBean.setPersistenceProvider(persistenceProvider);
        emFactoryBean.setJpaPropertyMap(jpaProperties());
        emFactoryBean.setPackagesToScan("cz.janys.foosballbot.entity");
        return emFactoryBean;
    }

    @Bean
    public PersistenceProvider persistenceProvider() {
        return new HibernatePersistenceProvider();
    }

    @Bean
    public Map<String, Object> jpaProperties() {
        return new HashMap<String, Object>() {{
            put("hibernate.dialect", prop("db.dialect"));
            put("hibernate.hbm2ddl.auto", prop("db.schema.auto_update"));
            put("hibernate.show_sql", prop("db.sql.show"));
            put("hibernate.format_sql", prop("db.sql.format"));
            put("hibernate.jdbc.batch_size", prop("db.jdbc.batch_size"));
        }};
    }

}
