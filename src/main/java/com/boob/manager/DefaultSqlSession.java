package com.boob.manager;

import com.boob.enums.SqlTypeEnum;
import com.boob.executor.DataBaseExecutor;

import java.util.UUID;

/**
 * @author jangbao - 2020/12/18 10:56
 */
public class DefaultSqlSession implements SqlSession {

    private EntityTransaction entityTransaction;

    private DataBaseExecutor dataBaseExecutor;

    private boolean isCommit = false;

    /**
     * 唯一码
     */
    private final String uuid = UUID.randomUUID().toString().substring(0, 8);

    public DefaultSqlSession(EntityTransaction entityTransaction, DataBaseExecutor dataBaseExecutor) {
        this.entityTransaction = entityTransaction;
        this.dataBaseExecutor = dataBaseExecutor;
    }

    public DefaultSqlSession() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T find(Class<T> clazz, Object id) {
        return (T) dataBaseExecutor.doExecute(id, clazz, SqlTypeEnum.FIND);
    }


    @Override
    public void persist(Object obj) {
        Class<?> clazz = obj.getClass();
        dataBaseExecutor.doExecute(obj, clazz, SqlTypeEnum.PERSIST);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T removeById(Class<T> clazz, Object id) {
        return (T) dataBaseExecutor.doExecute(id, clazz, SqlTypeEnum.REMOVE);

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T merge(T obj) {
        Class<?> clazz = obj.getClass();
        return (T) dataBaseExecutor.doExecute(obj, clazz, SqlTypeEnum.MERGE);
    }

    @Override
    public void commit() {
        entityTransaction.commit();
        this.isCommit = true;
    }

    @Override
    public void rollback() {
        entityTransaction.rollback();
    }

    @Override
    public void close() {
        if (!isCommit) {
            this.commit();
        }
        entityTransaction.close();
    }
}
