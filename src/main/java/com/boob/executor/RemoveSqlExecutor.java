package com.boob.executor;

import java.sql.SQLException;

/**
 * 删除执行器
 */
public class RemoveSqlExecutor extends AbstractSqlExecutor implements SqlExecutor {


    @Override
    protected void executeSql() throws SQLException {
        int remove = ps.executeUpdate();
        LOG.info("delete: " + sql);
        if (remove != 0) {
            LOG.info("删除 " + condition.getParam() + " 成功");
        }
    }

}
