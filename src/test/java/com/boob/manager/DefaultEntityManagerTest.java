package com.boob.manager;

import com.boob.configuration.Persistence;
import com.boob.factory.EntityManagerFactory;
import com.boob.transaction.EntityTransaction;
import model.entity.User;
import org.junit.Test;

/**
 * EntityManager默认实现类
 */
public class DefaultEntityManagerTest {

    @Test
    public void findTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        User user = entityManager.find(User.class, 1);
        System.out.println(user);
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    public void persistTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        User user = new User();
        user.setName("李四");
        user.setAge(18);
        entityManager.persist(user);
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    public void mergeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        User user = new User();
        user.setId(12);
        user.setName("王五");
        user.setAge(20);
        entityManager.merge(user);
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    public void removeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        User user = new User();
        user.setId(21);
        entityManager.remove(user);
        entityTransaction.commit();
        entityManager.close();
    }
}
