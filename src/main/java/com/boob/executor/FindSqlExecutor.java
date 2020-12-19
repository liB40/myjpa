package com.boob.executor;

import com.boob.handler.TypeHandler;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Map;

/**
 * 查询执行器
 */
public class FindSqlExecutor extends AbstractSqlExecutor implements SqlExecutor {

    @Override
    public void executeSql() throws SQLException {
        this.rs = ps.executeQuery();
        LOG.info("SELECT: " + sql);
    }

    @Override
    protected void handleResult() throws SQLException {
        try {
            rs.next();
            Map<String, String> columns = entity.getColumns();
            for (String key : columns.keySet()) {
                String columnName = columns.get(key);
                //获取字段
                Field field = entity.getClazz().getDeclaredField(key);
                //拿到结果
                Object object = TypeHandler.handleResultSet(rs, field.getType(), columnName);
                //打开权限
                field.setAccessible(true);
                //给结果赋值
                field.set(result, object);
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOG.warn(e.getMessage());
        }

    }

}
