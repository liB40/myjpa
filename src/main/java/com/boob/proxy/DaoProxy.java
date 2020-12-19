package com.boob.proxy;

import com.boob.manager.EntityManager;
import com.boob.model.Dao;
import com.boob.model.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class DaoProxy implements InvocationHandler {

    /**
     * 被代理对象dao信息
     */
    private Dao dao;

    /**
     * 被代理对象model中的entity
     */
    private Entity entity;

    /**
     * entityManager
     */
    private EntityManager entityManager;

    public DaoProxy(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 根据字节码生成实体类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getInstance(Class<T> clazz) {
        //获取对象的dao
        this.dao = entityManager.getDao(clazz);
        this.entity = entityManager.getEntity(dao.getEntityClass());
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        //TODO 解耦这里的方法
        String methodName = method.getName();
        if ("toString".equals(methodName)) {
            return Arrays.toString(proxy.getClass().getInterfaces());
        } else if ("findById".equals(methodName)) {
            return entityManager.find(entity.getClazz(), args[0]);
        } else if ("save".equals(methodName)) {
            entityManager.persist(args[0]);
        } else if ("update".equals(methodName)) {
            entityManager.merge(args[0]);
        } else if ("deleteById".equals(methodName)) {
            return entityManager.removeById(entity.getClazz(), args[0]);
        }
        return null;
    }
}
