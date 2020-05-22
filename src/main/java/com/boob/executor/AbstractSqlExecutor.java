package com.boob.executor;

import com.boob.handler.TypeHandler;
import com.boob.model.Condition;
import com.boob.model.Entity;
import com.boob.model.EntityAndCondition;
import com.boob.model.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * SqlExecutor抽象层
 */
public abstract class AbstractSqlExecutor implements SqlExecutor {

    /**
     * 日志类
     */
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractSqlExecutor.class);

    /**
     * connection连接
     */
    protected Connection connection;

    /**
     * sql
     */
    protected Sql sql;

    /**
     * statement
     */
    protected PreparedStatement ps;

    /**
     * 结果集
     */
    protected ResultSet rs;

    /**
     * 实体类信息
     */
    protected Entity entity;

    /**
     * 执行条件
     */
    protected Condition condition;

    /**
     * 返回值
     */
    protected Object result;


    /**
     * 创建语句集
     *
     * @throws SQLException
     */
    protected void create() throws SQLException {
        this.ps = connection.prepareStatement(sql.getSql());
    }

    /**
     * 释放资源
     *
     * @throws SQLException
     */
    protected void close() throws SQLException {
        if (ps != null) {
            ps.close();
        }
        if (rs != null) {
            rs.close();
        }
    }

    /**
     * 设置参数
     */
    protected void setParam() throws SQLException {
        List<Object> params = sql.getParams();
        for (int i = 0; i < params.size(); i++) {
            TypeHandler.handleParam(ps, i + 1, params.get(i));
        }
    }

    ;

    /**
     * 执行sql
     */
    protected abstract void executeSql() throws SQLException;

    /**
     * 处理结果集
     */
    protected void handleResult() throws SQLException {

    }

    @Override
    public void execute() throws SQLException {

        try {
            //创建statement
            create();
            //设置参数
            setParam();
            //执行sql
            executeSql();
            //处理结果集
            handleResult();
        } finally {
            //关闭rs,ps
            close();
        }
    }

    /**
     * 执行
     *
     * @param connection         sql连接
     * @param sql                sql 语句
     * @param entityAndCondition 类信息和执行条件
     * @return
     */
    @Override
    public Object execute(Connection connection, Sql sql, EntityAndCondition entityAndCondition) throws SQLException {
        this.sql = sql;
        this.connection = connection;
        this.entity = entityAndCondition.getEntity();
        this.condition = entityAndCondition.getCondition();
        this.result = entityAndCondition.getCondition().getReturnValue();
        execute();
        return this.result;
    }


}
