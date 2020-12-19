package com.boob.executor;

import com.boob.converter.ConverterFactory;
import com.boob.enums.SqlTypeEnum;
import com.boob.model.Condition;
import com.boob.model.Dao;
import com.boob.model.Entity;
import com.boob.model.EntityAndCondition;
import com.boob.model.Sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author jangbao - 2020/12/18 13:27
 */
public class DataBaseExecutor {

    /**
     * entity集合
     */
    private Map<Class, Entity> entityMap;

    /**
     * dao集合
     */
    private Map<Class, Dao> daoMap;

    /**
     * 数据库连接
     */
    private Connection connection;


    public DataBaseExecutor(Map<Class, Entity> entityMap, Map<Class, Dao> daoMap, Connection connection) {
        this.entityMap = entityMap;
        this.daoMap = daoMap;
        this.connection = connection;
    }

    /**
     * 执行操作
     *
     * @param param       参数
     * @param clazz       实体类字节码
     * @param sqlTypeEnum 操作类型
     */
    public Object doExecute(Object param, Class<?> clazz, SqlTypeEnum sqlTypeEnum) {
        checkEntity(clazz);
        Entity entity = entityMap.get(clazz);

        //获取实体类信息和执行条件封装类
        EntityAndCondition entityAndCondition = getEntityAndCondition(clazz, param, entity);

        //获取sql
        Sql sql = ConverterFactory.getSqlConverterByType(sqlTypeEnum).convert(entityAndCondition);

        //获取执行器
        try {
            return ExecutorFactory.getSqlExecutorByType(sqlTypeEnum).execute(connection, sql, entityAndCondition);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
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
    private void checkEntity(Class clazz) {
        if (!entityMap.containsKey(clazz)) {
            throw new IllegalArgumentException(clazz + " 不是Entity实体类");
        }
    }

}
