package com.curso.spring.rest.conf;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

@PropertySource(value = "classpath:datasource.properties")
@Configuration
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

    @Value(value = "${db.driver}")
    private String driver;

    @Value(value = "${ruta.script.inicial}")
    private String rutaScript;

    @Value(value = "${db.esquema.creado}")
    private Boolean creado;

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
        dataSource.setDriverClassName(driver);

        // crear base de datos
        if (!creado) {
            crearEsquema(dataSource);
        }
        dataSource.setSchema(esquema);

        return dataSource;
    }

    private void crearEsquema(DriverManagerDataSource dataSource) {
        Resource initSchema = new ClassPathResource(rutaScript);

        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        // ponemos la variable creado a true para no repetir el proceso
        setEsquemaCreado(true);
    }

    private void setEsquemaCreado(Boolean creado) {
        try {
            PropertiesConfiguration conf = new PropertiesConfiguration("datasource.properties");
            conf.setProperty("db.esquema.creado", creado);
            persistProperties(conf, creado);
            this.creado = creado;
        } catch (ConfigurationException | IOException e) {
            e.printStackTrace();
            LOG.error("Error al cambiar la configuración del fichero datasource.properties");
        }
    }

    private void persistProperties(PropertiesConfiguration conf, Boolean creado) throws IOException, ConfigurationException {
        PropertiesPersister persister = new DefaultPropertiesPersister();
        OutputStream os = new FileOutputStream(new File("src/main/resources/datasource.properties"));

        Properties properties = new Properties();
        properties.setProperty("db.esquema.creado", creado.toString());

        persister.store(properties, os, "Se modifica la propiedad 'db.esquema.creado'");
        conf.save();

        os.flush();
        os.close();

        LOG.info("db.esquema.creado = " + creado);
    }


}
