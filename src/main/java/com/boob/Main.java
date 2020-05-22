package com.boob;

import com.boob.configuration.Persistence;
import com.boob.enums.SqlTypeEnum;
import com.boob.executor.ExecutorFactory;
import com.boob.executor.SqlExecutor;
import com.boob.factory.EntityManagerFactory;
import com.boob.manager.EntityManager;
import com.boob.model.Entity;
import model.entity.User;
import model.entity.UserType;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 主测试类
 */
public class Main {

    public static void main(String[] args) {
//        test01();

        test02();
    }

    private static void test02() {
        MyProxy myProxy = new MyProxy();
        User instance = myProxy.getInstance(new User());
        System.out.println(instance);

    }

    static class MyProxy implements InvocationHandler {


        public User getInstance(User o) {
            return (User) Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
    }


    private static void test01() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        for (Map.Entry<Class, Entity> entry : entityManager.getEntityManagerFactory().getEntityMap().entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        System.out.println(entityManager);
    }
}
