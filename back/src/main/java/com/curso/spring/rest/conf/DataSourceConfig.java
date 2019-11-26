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

@PropertySource(value = "classpath:datasource.properties")
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value(value = "${db.url}")
    private String url;

    @Value(value = "${db.user}")
    private String username;

    @Value(value = "${db.password}")
    private String password;

    @Value(value = "${db.schema}")
    private String esquema;

    @Value(value = "${db.conector}")
    private String conector;

    @Value(value = "${db.host}")
    private String host;

    @Value(value = "${db.params}")
    private String params;

    @Value(value = "${db.driver}")
    private String driver;

    @Value(value = "${ruta.script.inicial}")
    private String rutaScript;

    @Value(value = "${db.dialect}")
    private String dialecto;

    @Value(value = "${db.ddl-auto}")
    private String ddlAuto;

    @Value(value = "${db.show-sql}")
    private String showSql;

    @Value(value = "${db.new-id-generator}")
    private Boolean newIdGenerator;

    @Value(value = "${db.esquema.creado}")
    private Boolean creado;

    @Value(value = "${ruta.datasource.properties}")
    private String rutaProperties;

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(getUrl(), username, password);
        dataSource.setDriverClassName(driver);

        // crear base de datos
        if (!creado) {
            crearEsquema(dataSource);
            setUrl();
            dataSource.setUrl(url);
        }

        comprobarCreacion(dataSource);
        dataSource.setSchema(esquema);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan("com.curso.spring.rest.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaProperties(configuracionesAdicionales());

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
        Resource initSchema = new ClassPathResource(rutaScript);

        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        // ponemos la variable creado a true para no repetir el proceso
        setEsquemaCreado(true);
    }

    private void setEsquemaCreado(Boolean creado) {
        guardarConf("db.esquema.creado", creado);
        this.creado = creado;
    }

    private String getUrl() {
        if (url.isEmpty()) {
            setUrl();
        }
        return url;
    }

    private void setUrl() {
        if (creado) {
            url = conector + host + esquema + params;
        } else {
            url =  conector + host + params;
        }

        guardarConf("db.url", url);
    }

    private void guardarConf(String propiedad, Object valor) {

        try {
            PropertiesConfiguration conf = new PropertiesConfiguration(rutaProperties);
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

        properties.setProperty("hibernate.dialect", dialecto);
        properties.setProperty("hibernate.ddl-auto", ddlAuto);
        properties.setProperty("show-sql", showSql);
        properties.setProperty("hibernate.use-new-id-generator-mappings", newIdGenerator.toString());

        return properties;
    }

    private boolean comprobarCreacion(DriverManagerDataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            connection.getCatalog();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            setEsquemaCreado(false);
            crearEsquema(dataSource);
            return false;
        }
    }

}
