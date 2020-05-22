package com.boob.executor;

import com.boob.enums.SqlTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行器工厂
 */
public class ExecutorFactory {
    /**
     * sql执行器map
     */
    private static final Map<SqlTypeEnum, SqlExecutor> SQL_EXECUTOR_MAP = new HashMap<>();

    static {

        SQL_EXECUTOR_MAP.put(SqlTypeEnum.FIND, new FindSqlExecutor());
        SQL_EXECUTOR_MAP.put(SqlTypeEnum.PERSIST, new PersistSqlExecutor());
        SQL_EXECUTOR_MAP.put(SqlTypeEnum.MERGE, new MergeSqlExecutor());
        SQL_EXECUTOR_MAP.put(SqlTypeEnum.REMOVE, new RemoveSqlExecutor());

    }

    /**
     * 根据类型获取执行器
     *
     * @param sqlTypeEnum
     * @return
     */
    public static SqlExecutor getSqlExecutorByType(SqlTypeEnum sqlTypeEnum) {
        return SQL_EXECUTOR_MAP.get(sqlTypeEnum);
    }
}
