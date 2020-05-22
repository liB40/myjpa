package com.boob.manager;


import com.boob.factory.EntityManagerFactory;
import com.boob.model.Dao;
import com.boob.model.Entity;
import com.boob.transaction.EntityTransaction;


/**
 * EntityManager接口
 */
public interface EntityManager {

    /**
     * 即时查询
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    <T> T find(Class<T> clazz, Object id);

    /**
     * 保存
     *
     * @param obj
     */
    void persist(Object obj);

    /**
     * 删除
     *
     * @param obj
     */
    void remove(Object obj);


    /**
     * 修改
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> T merge(T obj);


    /**
     * 获取事务管理
     *
     * @return
     */
    EntityTransaction getTransaction();


    /**
     * 获取创建工厂
     *
     * @return
     */
    EntityManagerFactory getEntityManagerFactory();

    /**
     * 是否需要回滚
     *
     * @return
     */
    boolean needRollback();

    /**
     * 关闭连接释放资源
     */
    void close();

    /**
     * 获取Dao对象
     *
     * @param clazz
     * @return
     */
    Dao getDao(Class clazz);

    /**
     * 获取Entity对象
     *
     * @param clazz
     * @return
     */
    Entity getEntity(Class clazz);


    /**
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> clazz);
}
