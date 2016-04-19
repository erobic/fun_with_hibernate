package com.erobic.fun_with_hibernate.runner;

import com.erobic.fun_with_hibernate.config.DbConfig;
import com.erobic.fun_with_hibernate.entity.SimpleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robik on 4/9/16.
 */
public class UserBulkInsertionRunner {

    DbConfig dbConfig = new DbConfig();
    EntityManager entityManager;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) throws IOException {
        UserBulkInsertionRunner runner = new UserBulkInsertionRunner();
        runner.insertUsers(1000);
    }

    private void insertUsers(int noOfUsers) throws IOException {
        entityManager = dbConfig.getEntityManager();
        entityManager.getTransaction().begin();
        List<SimpleEntity> users = new ArrayList();
        logger.info("Persisting {} users", noOfUsers);
        long beforePersisting = System.currentTimeMillis();

        for (int i = 0; i < noOfUsers; i++) {
            SimpleEntity user = createUser(i);
            entityManager.persist(user);
        }
        long afterPersisting = System.currentTimeMillis();
        logger.info("Flushing {} users", users.size());
        entityManager.flush();
        long afterFlushing = System.currentTimeMillis();
        logger.info("Took: {} ms to persist {} records. And then took: {} ms to flush the records."
                , (afterPersisting - beforePersisting), noOfUsers, (afterFlushing - afterPersisting));
        entityManager.getTransaction().commit();
    }

    private SimpleEntity createUser(int i) {
        SimpleEntity user = new SimpleEntity();
        user.setUsername("username" + i);
        user.setPassword("password" + i);
        return user;
    }


}
