package com.boob.manager;

import com.boob.configuration.PersistenceUnit;
import com.boob.converter.ConverterFactory;
import com.boob.enums.SqlTypeEnum;
import com.boob.executor.ExecutorFactory;
import com.boob.factory.EntityManagerFactory;
import com.boob.model.*;
import com.boob.proxy.DaoProxy;
import com.boob.transaction.DefaultEntityTransaction;
import com.boob.transaction.EntityTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
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
     * 事务支持
     */
    private EntityTransaction entityTransaction;

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
     * 是否发生了异常
     */
    private boolean exceptionHappen = false;

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
        //初始化事务管理器
        this.entityTransaction = new DefaultEntityTransaction(connection, this);

        initMapper();
    }

    /**
     * 初始化mapper集合
     */
    private void initMapper() {
        mapperMap = new HashMap<>();
        for (Class clazz : daoMap.keySet()) {
            mapperMap.put(clazz, new DaoProxy(this).getInstance(clazz));
        }
    }

    @Override
    public <T> T find(Class<T> clazz, Object id) {
        return (T) doExecute(id, clazz, SqlTypeEnum.FIND);
    }


    @Override
    public void persist(Object obj) {
        Class<?> clazz = obj.getClass();
        doExecute(obj, clazz, SqlTypeEnum.PERSIST);
    }

    @Override
    public void remove(Object obj) {
        Class<?> clazz = obj.getClass();
        doExecute(obj, clazz, SqlTypeEnum.REMOVE);

    }

    @Override
    public <T> T merge(T obj) {
        Class<?> clazz = obj.getClass();
        return (T) doExecute(obj, clazz, SqlTypeEnum.MERGE);
    }

    /**
     * 执行操作
     *
     * @param obj         参数
     * @param clazz       实体类字节码
     * @param sqlTypeEnum 操作类型
     */
    private Object doExecute(Object obj, Class<?> clazz, SqlTypeEnum sqlTypeEnum) {

        //如果操作是写类型并且未开启事务
        if (sqlTypeEnum.isWrite() && !checkTransaction()) {
            return null;
        }
        checkEntity(clazz);
        Entity entity = entityMap.get(clazz);

        //获取实体类信息和执行条件封装类
        EntityAndCondition entityAndCondition = getEntityAndCondition(clazz, obj, entity);

        //获取sql
        Sql sql = ConverterFactory.getSqlConverterByType(sqlTypeEnum).convert(entityAndCondition);

        //获取执行器
        try {
            return ExecutorFactory.getSqlExecutorByType(sqlTypeEnum).execute(connection, sql, entityAndCondition);
        } catch (SQLException e) {
            //发生了异常
            exceptionHappen = true;
            LOG.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 是否需要回滚事务
     *
     * @return
     */
    public boolean needRollback() {
        return exceptionHappen;
    }

    /**
     * 获取EntityAndCondition
     *
     * @param clazz  返回值字节码
     * @param param  参数
     * @param entity 实体类信息
     * @param <T>
     * @return
     */
    private <T> EntityAndCondition getEntityAndCondition(Class<T> clazz, Object param, Entity entity) {
        return new EntityAndCondition(entity, new Condition(param, clazz));
    }

    /**
     * 检验是不是entity实体类
     *
     * @param clazz 实体类字节码
     * @return
     */
    public void checkEntity(Class clazz) {
        if (!entityMap.containsKey(clazz)) {
            throw new IllegalArgumentException(clazz + " 不是Entity实体类");
        }
    }

    /**
     * 检验是不是该类是不是dao类
     *
     * @param clazz 实体类字节码
     * @return
     */
    public void checkDao(Class clazz) {
        if (!daoMap.containsKey(clazz)) {
            throw new IllegalArgumentException(clazz + " 不是dao类");
        }
    }

    @Override
    public Entity getEntity(Class clazz) {
        checkEntity(clazz);
        return entityMap.get(clazz);
    }

    @Override
    public Dao getDao(Class clazz) {
        checkDao(clazz);
        return daoMap.get(clazz);
    }

    @Override
    public <T> T getMapper(Class<T> clazz) {
        return (T) mapperMap.get(clazz);
    }

    /**
     * 检查事务是否开启
     */
    private boolean checkTransaction() {
        return this.getTransaction().isActive();
    }

    @Override
    public EntityTransaction getTransaction() {
        return this.entityTransaction;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return this.entityManagerFactory;
    }

    @Override
    public void close() {
        //归还连接
        entityManagerFactory.putConnection(connection);
    }
}
