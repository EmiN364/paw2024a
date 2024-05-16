package ar.edu.itba.paw.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//@EnableAsync
//@EnableScheduling
//@EnableCaching
@EnableTransactionManagement
@Configuration
@EnableWebMvc
@ComponentScan({ "ar.edu.itba.paw.webapp.controller",
    "ar.edu.itba.paw.services", "ar.edu.itba.paw.persistence" })
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("classpath:sql/schema.sql")
    private Resource schemaSql;

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new
                InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        // LocaleContextHolder.getLocale()
        return viewResolver;
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();

        ms.addBasenames("classpath:i18n/messages");
        ms.setDefaultEncoding(StandardCharsets.UTF_8.name());
//        ms.setCacheSeconds((int) TimeUnit.MINUTES.toSeconds(5));
        ms.setCacheSeconds((int) 5);

        return ms;
    }

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        /*ds.setUrl("jdbc:postgresql://localhost/postgres");
        ds.setUsername("postgres");*/
        ds.setUrl("jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com/postgres");
        ds.setUsername("postgres.ntzhaiwmzzlckeuszsro");
        ds.setPassword("PawDB123!$$$");
        ds.setSchema("clases");
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
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");

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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }

    @Bean
    public DataSourceInitializer dsInitializer(DataSource ds) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(ds);
        initializer.setDatabasePopulator(dsPopulator());
        //  initializer.setDatabaseCleaner(null);
        return initializer;

    }

    private DatabasePopulator dsPopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        populator.addScript(schemaSql);

        return populator;
    }

    /*@Bean
    public UserService userService() {
        return new UserServiceImpl();
    }*/
}
