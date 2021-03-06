package com.boob.dao;

import com.boob.configuration.Persistence;
import com.boob.factory.EntityManagerFactory;
import com.boob.manager.EntityManager;
import model.dao.UserDao;
import model.entity.User;
import org.junit.Test;

public class UserDaoTest {

    @Test
    public void findByIdTest() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager em = emf.createEntityManager();
        UserDao userDao = em.getMapper(UserDao.class);
        User user = userDao.findById(1);
        em.close();
        System.out.println(user);
    }

    @Test
    public void saveTest() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager em = emf.createEntityManager();
        UserDao userDao = em.getMapper(UserDao.class);
        User user = new User();
        user.setName("古力娜扎");
        user.setAge(78);
        userDao.save(user);
        em.close();
        System.out.println(user);
    }

    @Test
    public void updateTest() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager em = emf.createEntityManager();
        UserDao userDao = em.getMapper(UserDao.class);
        User user = new User();
        user.setId(2);
        user.setName("zhangsan");
        user.setAge(53);
        userDao.update(user);
        em.close();
        System.out.println(user);
    }

    @Test
    public void deleteByIdTest() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager em = emf.createEntityManager();
        UserDao userDao = em.getMapper(UserDao.class);
        userDao.deleteById(2);
        userDao.deleteById(3);
        em.close();
        EntityManager em2 = emf.createEntityManager();
        UserDao userDao2 = em2.getMapper(UserDao.class);
        userDao2.deleteById(2);
        userDao2.deleteById(3);
        em2.close();
        System.out.println();
    }
}
