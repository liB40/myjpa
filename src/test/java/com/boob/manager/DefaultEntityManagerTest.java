package com.boob.manager;

import com.boob.configuration.Persistence;
import com.boob.factory.EntityManagerFactory;
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
        User user = entityManager.find(User.class, 1);
        System.out.println(user);
        entityManager.close();
    }

    @Test
    public void persistTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        User user = new User();
        user.setName("李四");
        user.setAge(18);
        entityManager.persist(user);
        entityManager.close();
    }

    @Test
    public void mergeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        User user = new User();
        user.setId(12);
        user.setName("王五");
        user.setAge(20);
        entityManager.merge(user);
        entityManager.close();
    }

    @Test
    public void removeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.removeById(User.class, 2);
        entityManager.close();
    }
}
