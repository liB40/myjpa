package com.boob.executor;

import java.sql.SQLException;

/**
 * 插入执行器
 */
public class PersistSqlExecutor extends AbstractSqlExecutor implements SqlExecutor {


    @Override
    protected void executeSql() throws SQLException {
        int persist = ps.executeUpdate();
        LOG.info("INSERT: " + sql);
        LOG.info("插入影响行数: " + persist);
    }
}
