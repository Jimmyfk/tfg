package com.curso.spring.rest.conf;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@PropertySource("classpath:datasource.properties")
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.schema}")
    private String esquema;

    @Value("${db.conector}")
    private String conector;

    @Value("${db.host}")
    private String host;

    @Value("${db.params}")
    private String params;

    @Value("${db.driver}")
    private String driver;

    @Value("${ruta.script.inicial}")
    private String rutaScript;

    @Value("${db.dialect}")
    private String dialecto;

    @Value("${db.ddl-auto}")
    private String ddlAuto;

    @Value("${db.show-sql}")
    private String showSql;

    @Value("${db.new-id-generator}")
    private Boolean newIdGenerator;

    @Value("${db.esquema.creado}")
    private Boolean creado;

    @Value("${ruta.datasource.properties}")
    private String rutaProperties;

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(this.getUrl(), this.username, this.password);
        dataSource.setDriverClassName(this.driver);

        // crear base de datos
        if (!this.creado) {
            this.crearEsquema(dataSource);
            this.setUrl();
            dataSource.setUrl(this.url);
        }

        this.comprobarCreacion(dataSource);
        dataSource.setSchema(this.esquema);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(this.dataSource());
        entityManager.setPackagesToScan("com.curso.spring.rest.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaProperties(this.configuracionesAdicionales());

        return entityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    private void crearEsquema(DriverManagerDataSource dataSource) {
        Resource initSchema = new ClassPathResource(this.rutaScript);

        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        // ponemos la variable creado a true para no repetir el proceso
        this.setEsquemaCreado(true);
    }

    private void setEsquemaCreado(Boolean creado) {
        this.guardarConf("db.esquema.creado", creado);
        this.creado = creado;
    }

    private String getUrl() {
        if (this.url.isEmpty()) {
            this.setUrl();
        }
        return this.url;
    }

    private void setUrl() {
        if (this.creado) {
            this.url = this.conector + this.host + this.esquema + this.params;
        } else {
            this.url = this.conector + this.host + this.params;
        }

        this.guardarConf("db.url", this.url);
    }

    private void guardarConf(String propiedad, Object valor) {

        try {
            PropertiesConfiguration conf = new PropertiesConfiguration(this.rutaProperties);
            conf.setProperty(propiedad, valor);
            conf.save();
            LOG.info(propiedad + " = " + valor);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            LOG.error("Error al cambiar la configuración del fichero datasource.properties");
        }
    }

    private Properties configuracionesAdicionales() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect", this.dialecto);
        properties.setProperty("hibernate.ddl-auto", this.ddlAuto);
        properties.setProperty("show-sql", this.showSql);
        properties.setProperty("hibernate.use-new-id-generator-mappings", this.newIdGenerator.toString());

        return properties;
    }

    private boolean comprobarCreacion(DriverManagerDataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            connection.getCatalog();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            this.setEsquemaCreado(false);
            this.crearEsquema(dataSource);
            return false;
        }
    }

}
