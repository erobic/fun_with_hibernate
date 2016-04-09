package com.erobic.fun_with_hibernate.runner;

import com.erobic.fun_with_hibernate.config.DbConfig;
import com.erobic.fun_with_hibernate.entity.User;

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

    public static void main(String[] args) throws IOException {
        UserBulkInsertionRunner runner = new UserBulkInsertionRunner();
//        runner.insertUsers(1);
//        runner.insertUsers(10);
//        runner.insertUsers(100);
//        runner.insertUsers(1000);
//        runner.insertUsers(10000);
//        runner.insertUsers(20000);//4058
//        runner.insertUsers(30000);//5441
//        runner.insertUsers(40000);//7301, 9848
//        runner.insertUsers(40000);//7301, 9848
//        runner.insertUsers(40000);//7301, 9848
        runner.insertUsers(40000);//7.5 secs
        runner.insertUsers(1000000);//191076 ms
    }

    private void insertUsers(int noOfUsers) throws IOException {
        entityManager = dbConfig.getEntityManager();
        entityManager.getTransaction().begin();
        List<User> users = new ArrayList();
        System.out.println("Persisting "+noOfUsers+" users");
        long beforePersisting = System.currentTimeMillis();

        for (int i = 0; i < noOfUsers; i++) {
            User user = createUser(i);
            entityManager.persist(user);
        }
        long afterPersisting = System.currentTimeMillis();
        System.out.println("Flushing "+users.size()+" users");
        entityManager.flush();
        long afterFlushing = System.currentTimeMillis();
        System.out.println("Took: "+(afterPersisting-beforePersisting)+" ms to persist "+noOfUsers+" records. And then took: "+(afterFlushing-afterPersisting)+" ms to flush the records.");
        entityManager.getTransaction().commit();
    }

    private User createUser(int i) {
        User user = new User();
        user.setUsername("username" + i);
        user.setPassword("password" + i);
        return user;
    }


}
