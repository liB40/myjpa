package com.boob.manager;

import com.boob.configuration.PersistenceUnit;
import com.boob.converter.ConverterFactory;
import com.boob.enums.SqlTypeEnum;
import com.boob.executor.DataBaseExecutor;
import com.boob.executor.ExecutorFactory;
import com.boob.factory.EntityManagerFactory;
import com.boob.model.Condition;
import com.boob.model.Dao;
import com.boob.model.Entity;
import com.boob.model.EntityAndCondition;
import com.boob.model.Sql;
import com.boob.proxy.DaoProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Jangbao
 * EntityManager默认实现类
 */
public class DefaultEntityManager implements EntityManager {

    /**
     * 日志类
     */
    private static final Logger LOG = LoggerFactory.getLogger(DefaultEntityManager.class);

    /**
     * 配置单元
     */
    private PersistenceUnit persistenceUnit;

    /**
     * 工厂类
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * 数据库连接
     */
    private Connection connection;

    /**
     * entity集合
     */
    private Map<Class, Entity> entityMap;

    /**
     * dao集合
     */
    private Map<Class, Dao> daoMap;

    /**
     * 被代理的Dao集合
     */
    private Map<Class, Object> mapperMap;

    /**
     * 最后一个事务是否已提交
     */
    private boolean isClose;

    /**
     * 数据库执行器
     */
    private DataBaseExecutor dataBaseExecutor;

    public DefaultEntityManager() {

    }

    public DefaultEntityManager(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.persistenceUnit = entityManagerFactory.getPersistenceUnit();
        this.connection = entityManagerFactory.getConnection();
        this.entityMap = entityManagerFactory.getEntityMap();
        this.daoMap = entityManagerFactory.getDaoMap();
        initMappers();
        this.dataBaseExecutor = new DataBaseExecutor(entityMap, daoMap, connection);
    }

    /**
     * 初始化mapper集合
     */
    private void initMappers() {
        mapperMap = new HashMap<>();
        for (Class clazz : daoMap.keySet()) {
            mapperMap.put(clazz, new DaoProxy(this).getInstance(clazz));
        }
    }

    @Override
    public SqlSession openSession() {
        if (isClose) {
            throw new RuntimeException("entityManager 已经关闭了，无法使用操作数据库功能");
        }
        Connection connection = entityManagerFactory.getConnection();
        DefaultEntityTransaction entityTransaction = new DefaultEntityTransaction(connection, this);
        entityTransaction.begin();
        return new DefaultSqlSession(entityTransaction, new DataBaseExecutor(this.entityMap, this.daoMap, connection));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T find(Class<T> clazz, Object id) {
        return (T) doExecute(id, clazz, SqlTypeEnum.FIND);
    }


    @Override
    public void persist(Object obj) {
        Class<?> clazz = obj.getClass();
        doExecute(obj, clazz, SqlTypeEnum.PERSIST);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T removeById(Class<T> clazz, Object id) {
        return (T) doExecute(id, clazz, SqlTypeEnum.REMOVE);

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T merge(T obj) {
        Class<?> clazz = obj.getClass();
        return (T) doExecute(obj, clazz, SqlTypeEnum.MERGE);
    }


    /**
     * entityManager执行操作
     *
     * @param param       参数
     * @param clazz       实体类字节码
     * @param sqlTypeEnum 操作类型
     */
    protected Object doExecute(Object param, Class<?> clazz, SqlTypeEnum sqlTypeEnum) {
        if (isClose) {
            throw new RuntimeException("entityManager 已经关闭了，无法使用操作数据库功能");
        }
        return this.dataBaseExecutor.doExecute(param, clazz, sqlTypeEnum);
    }


    /**
     * 检验是不是该类是不是dao类
     *
     * @param clazz 实体类字节码
     * @return
     */
    private void checkDao(Class clazz) {
        if (!daoMap.containsKey(clazz)) {
            throw new IllegalArgumentException(clazz + " 不是dao类");
        }
    }

    @Override
    public Entity getEntity(Class clazz) {
        return entityMap.get(clazz);
    }

    @Override
    public Dao getDao(Class clazz) {
        checkDao(clazz);
        return daoMap.get(clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clazz) {
        return (T) mapperMap.get(clazz);
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return this.entityManagerFactory;
    }

    @Override
    public void close() {
        //归还连接
        entityManagerFactory.putConnection(connection);
        this.connection = null;
    }
}
