package com.boob.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

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

    /**
     * 唯一码
     */
    private final String uuid = UUID.randomUUID().toString().substring(0, 8);

    public DefaultEntityTransaction(Connection connection, EntityManager entityManager) {
        this.connection = connection;
        this.entityManager = entityManager;
    }

    @Override
    public void begin() {
        try {
            //设置为手动提交事务
            connection.setAutoCommit(false);
            LOG.info("事务开启 ===> id: " + uuid);
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
        }
        this.active = true;
    }

    @Override
    public void commit() {
        try {
            connection.commit();
            LOG.info("事务提交 ===> id: " + uuid);
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            this.rollback();
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
            LOG.info("事务回滚 ===> id: " + uuid);
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
        }
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void close() {
        entityManager.getEntityManagerFactory().putConnection(connection);
        this.connection = null;
        LOG.info("事务关闭 ===> id: " + uuid);
    }
}
