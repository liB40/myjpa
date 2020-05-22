package com.boob.transaction;

import com.boob.manager.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务支持
 */
public class DefaultEntityTransaction implements EntityTransaction {

    /**
     * 日志类
     */
    private static final Logger LOG = LoggerFactory.getLogger(DefaultEntityTransaction.class);

    /**
     * 数据库连接
     */
    private Connection connection;

    /**
     * 事务管理器
     */
    private EntityManager entityManager;

    /**
     * 事务是否已激活
     */
    private boolean active = false;

    public DefaultEntityTransaction(Connection connection, EntityManager entityManager) {
        this.connection = connection;
        this.entityManager = entityManager;
    }

    @Override
    public void begin() {
        try {
            //设置为手动提交事务
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
        }
        this.active = true;
    }

    @Override
    public void commit() {
        if (!this.active) {
            LOG.warn("事务未开启,无法提交");
            return;
        }
        //需要回滚
        if (entityManager.needRollback()) {
            LOG.warn("出现异常,回滚事务");
            this.rollback();
            return;
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            this.rollback();
        }
    }

    @Override
    public void rollback() {
        if (!this.active) {
            LOG.warn("事务未开启,无法回滚");
            return;
        }
        try {
            connection.rollback();
            LOG.info("事务成功回滚");
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
        }
    }

    @Override
    public boolean isActive() {
        return this.active;
    }
}
