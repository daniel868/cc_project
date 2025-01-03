package org.business.config;

import org.business.model.Reservation;
import org.business.model.Restaurant;
import org.service.customer.model.Customer;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = {"org.business", "org.service"},
        entityManagerFactoryRef = "customEntityManager",
        transactionManagerRef = "transactionManager"
)
public class PersistenceConfig {
    private final DataSource dataSource;
    private final EntityManagerFactoryBuilder builder;

    public PersistenceConfig(DataSource dataSource, EntityManagerFactoryBuilder builder) {
        this.dataSource = dataSource;
        this.builder = builder;
    }

    /*
    use this custom entity manager in order to be able to pick up entities from customer packages
     */

    @Bean
    public LocalContainerEntityManagerFactoryBean customEntityManager() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.show_sql", "true");
        return builder.dataSource(dataSource)
                .packages(Customer.class, Reservation.class, Restaurant.class)
                .properties(properties)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

}
