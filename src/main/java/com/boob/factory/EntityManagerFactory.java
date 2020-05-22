package com.boob.factory;

import com.boob.configuration.PersistenceUnit;
import com.boob.manager.EntityManager;
import com.boob.model.Dao;
import com.boob.model.Entity;
import com.boob.scanner.DaoScanner;
import com.boob.scanner.EntityScanner;

import java.sql.Connection;
import java.util.Map;


/**
 * EntityManagerFactory接口
 */
public interface EntityManagerFactory {

    /**
     * 创建EntityManager
     *
     * @return
     */
    EntityManager createEntityManager();

    void setPersistenceUnit(PersistenceUnit persistenceUnit);

    PersistenceUnit getPersistenceUnit();

    Map<Class, Entity> getEntityMap();

    void setEntityMap(Map<Class, Entity> entityMap);

    EntityScanner getEntityScanner();

    void setEntityScanner(EntityScanner entityScanner);

    Map<Class, Dao> getDaoMap();

    void setDaoMap(Map<Class, Dao> daoMap);

    DaoScanner getDaoScanner();

    void setDaoScanner(DaoScanner daoScanner);

    /**
     * 获取连接
     *
     * @return
     */
    Connection getConnection();

    /**
     * 放回连接
     *
     * @return
     */
    void putConnection(Connection connection);
}
