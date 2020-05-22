package com.boob.executor;

import com.boob.handler.TypeHandler;

import java.lang.reflect.Field;
import java.sql.SQLException;

/**
 * 插入执行器
 */
public class PersistSqlExecutor extends AbstractSqlExecutor implements SqlExecutor {


    @Override
    protected void executeSql() throws SQLException {
        int persist = ps.executeUpdate();
        LOG.info("insert: " + sql);
        if (persist != 0) {
            LOG.info("插入 " + condition.getParam() + " 成功");
        }
    }
}
