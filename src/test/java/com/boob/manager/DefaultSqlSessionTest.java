package com.boob.manager;

import com.boob.configuration.Persistence;
import com.boob.factory.EntityManagerFactory;
import model.entity.User;
import org.junit.Test;

/**
 * @author jangbao - 2020/12/18 13:00
 */
public class DefaultSqlSessionTest {

    @Test
    public void findTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        SqlSession sqlSession = entityManager.openSession();
        User user = sqlSession.find(User.class, 1);
        System.out.println(user);
        sqlSession.close();
        entityManager.close();
    }

    @Test
    public void persistTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        SqlSession sqlSession = entityManager.openSession();
        User user = new User();
        user.setName("李四");
        user.setAge(18);
        sqlSession.persist(user);
        sqlSession.close();
        entityManager.close();
    }

    @Test
    public void mergeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        SqlSession sqlSession = entityManager.openSession();
        User user = new User();
        user.setId(1);
        user.setName("王五");
        user.setAge(20);
        sqlSession.merge(user);
        sqlSession.close();
        entityManager.close();
    }

    @Test
    public void removeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        SqlSession sqlSession = entityManager.openSession();
        sqlSession.removeById(User.class, 1);
        sqlSession.close();
        entityManager.close();
    }
}
