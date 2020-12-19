package com.boob.manager;


import com.boob.factory.EntityManagerFactory;
import com.boob.model.Dao;
import com.boob.model.Entity;


/**
 * EntityManager接口
 */
public interface EntityManager extends OperateDataBaseAble {

    /**
     * 获取创建工厂
     *
     * @return
     */
    EntityManagerFactory getEntityManagerFactory();

    /**
     * 关闭连接释放资源
     */
    void close();

    /**
     * 开启sqlSession
     *
     * @return
     */
    SqlSession openSession();

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
     * 获取Mapper 对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> clazz);
}
