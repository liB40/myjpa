package com.boob.executor;

import java.sql.SQLException;

/**
 * 更新执行器
 */
public class MergeSqlExecutor extends AbstractSqlExecutor implements SqlExecutor {


    @Override
    protected void executeSql() throws SQLException {
        int merge = ps.executeUpdate();
        LOG.info("UPDATE: " + sql);
        LOG.info("修改影响行数: " + merge);
    }

}
