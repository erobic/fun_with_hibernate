package com.erobic.fun_with_hibernate.runner;

import com.erobic.fun_with_hibernate.config.DbConfig;
import com.erobic.fun_with_hibernate.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;

/**
 * Created by robik on 4/9/16.
 */
/*
Instructions:
We need to enable hibernate statistics generation, so:

2. Use this line in hibernate.properties:
hibernate.generate_statistics=true
 */
public class HibernateStatisticsUsingSessionFactoryRunner {

    DbConfig dbConfig = new DbConfig();
    EntityManager entityManager;

    public static void main(String[] args) throws IOException {
        HibernateStatisticsUsingSessionFactoryRunner runner = new HibernateStatisticsUsingSessionFactoryRunner();
        runner.generateStats(10);
    }

    public void generateStats(int noOfUsers) throws IOException {
        EntityManagerFactory entityManagerFactory = dbConfig.createEntityManagerFactory();
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Statistics statistics = sessionFactory.getStatistics();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            for(int i=0;i<noOfUsers;i++){
                User user = createUser(i);
                session.save(user);
            }
            transaction.commit();
            statistics.logSummary();
        }catch(Exception e){
            transaction.rollback();
        }finally {
            session.close();
            sessionFactory.close();
        }
    }

    private User insertUser(int i){
        User user = createUser(i);
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    private User createUser(int i) {
        User user = new User();
        user.setUsername("username" + i);
        user.setPassword("password" + i);
        return user;
    }

}
