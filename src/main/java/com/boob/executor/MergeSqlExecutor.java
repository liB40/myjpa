package com.boob.executor;

import java.sql.SQLException;

/**
 * 更新执行器
 */
public class MergeSqlExecutor extends AbstractSqlExecutor implements SqlExecutor {


    @Override
    protected void executeSql() throws SQLException {
        int merge = ps.executeUpdate();
        LOG.info("update: " + sql);
        if (merge != 0) {
            LOG.info("修改 " + condition.getParam() + " 成功");
        }
    }

}
