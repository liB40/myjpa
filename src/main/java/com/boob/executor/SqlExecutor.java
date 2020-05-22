package com.boob.executor;

import com.boob.model.EntityAndCondition;
import com.boob.model.Sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * sql 执行器
 */
public interface SqlExecutor extends Executor {

    /**
     * 执行
     *
     * @param connection         连接
     * @param sql                sql语句
     * @param entityAndCondition 条件
     * @return
     */
    Object execute(Connection connection, Sql sql, EntityAndCondition entityAndCondition) throws SQLException;

    @Override
    default void execute() throws SQLException {

    }
}
