package com.erobic.fun_with_hibernate.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by robik on 4/9/16.
 */
public class DbConfig {
    public static final String PERSISTENT_UNIT = "persistentUnit";

    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    private DataSource createDataSource() throws IOException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String filename = "db.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if(inputStream==null){
            throw new FileNotFoundException(filename);
        }

        Properties properties = new Properties();
        properties.load(inputStream);
        dataSource.setDriverClassName(properties.getProperty("db.driver"));
        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUsername(properties.getProperty("db.username"));
        dataSource.setPassword(properties.getProperty("db.password"));
        System.out.println("Created data source...");
        return dataSource;
    }

    private Properties createHibernateProperties() throws IOException {
        Properties properties = new Properties();
        String filename=  "hibernate.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if(inputStream==null){
            throw new FileNotFoundException(filename);
        }
        properties.load(inputStream);
        return properties;
    }

    public EntityManagerFactory createEntityManagerFactory() throws IOException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
                = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(createDataSource());
        entityManagerFactoryBean
                .setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(com.erobic.fun_with_hibernate.entity.EntityPkgPlaceholder.class.getPackage().getName());
        entityManagerFactoryBean.setPersistenceUnitName(PERSISTENT_UNIT);
        Properties jpaProperties = createHibernateProperties();
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean.getObject();
    }

    private EntityManagerFactory getEntityManagerFactory() throws IOException {
        if(entityManagerFactory!=null){
            return entityManagerFactory;
        }else{
            entityManagerFactory = createEntityManagerFactory();
            return entityManagerFactory;
        }
    }

    public EntityManager getEntityManager() throws IOException {
        if(entityManager!=null){
            return  entityManager;
        }else{
            entityManager = getEntityManagerFactory().createEntityManager();
            return entityManager;
        }
    }
}
