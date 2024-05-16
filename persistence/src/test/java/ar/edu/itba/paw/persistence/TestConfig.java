package ar.edu.itba.paw.persistence;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
@ComponentScan({"ar.edu.itba.paw.persistence"})
@Configuration
public class TestConfig {

    @Value("classpath:sql/pgsql.sql")
    private Resource pgsqlSyntax;

    @Value("classpath:sql/schema.sql")
    private Resource schemaSql;

    @Bean
    public DataSource dataSource() {
        final SingleConnectionDataSource ds = new SingleConnectionDataSource();

        ds.setDriverClassName(JDBCDriver.class.getName());
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("ha");
        ds.setPassword("ha");

        ds.setSuppressClose(true);

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("ar.edu.itba.paw.models");
        emf.setDataSource(dataSource());

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");

        // FIXME: Remove in prod!!
        properties.setProperty("hibernate.show_sql", "true"); // For debugging purposes
        properties.setProperty("format_sql", "true"); // For debugging purposes

        emf.setJpaProperties(properties);

        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public DataSourceInitializer dsInitializer(DataSource ds) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(ds);
        initializer.setDatabasePopulator(dsPopulator());
        //  initializer.setDatabaseCleaner(null);
        return initializer;
    }

    @Bean
    protected DatabasePopulator dsPopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        populator.addScript(pgsqlSyntax);
        populator.addScript(schemaSql);

        return populator;
    }
}
