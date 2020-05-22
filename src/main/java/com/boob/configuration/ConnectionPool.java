package com.boob.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库连接池
 */
public class ConnectionPool {

    /**
     * 日志类
     */
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPool.class);

    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool();

    private ConnectionPool() {

    }

    /**
     * 数据库连接信息
     */
    private static DataSource dataSource;

    /**
     * 连接池
     */
    private static final List<Connection> CONNECTION_LIST = new ArrayList<>();

    /**
     * 上次数据库池中数量大于10的时间
     */
    private static long lastTime;

    /**
     * 长时间多余10个连接将会缩减数据库
     */
    private static final int THRESHOLD = 10;

    /**
     * 缩减数据库5个连接
     */
    private static final int REMOVE_COUNT = 5;

    /**
     * 时间为一分钟
     */
    private static final long THRESHOLD_TIME = 60 * 1000;

    /**
     * 最小的连接池容量
     */
    private static int capacity = 5;

    /**
     * 获取连接池
     * 默认池中有10个连接
     *
     * @param dataSource
     * @return
     */
    public static ConnectionPool getInstance(DataSource dataSource) {
        return getInstance(dataSource, capacity);
    }

    /**
     * 获取连接池
     *
     * @param dataSource
     * @return
     */
    public static ConnectionPool getInstance(DataSource dataSource, int count) {
        ConnectionPool.dataSource = dataSource;
        //销毁原有的连接池
        destroy();
        //初始化新的连接池
        init(count);
        return CONNECTION_POOL;
    }

    /**
     * 使用DriverManager获取连接
     *
     * @return
     * @throws SQLException
     */
    private static Connection getConnectionByDriverManager() {
        try {
            return DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接信息有误:" + e.getMessage());
        }
    }


    /**
     * 初始化连接池
     *
     * @param count
     */
    private static void init(int count) {
        //如果建立的数量小于capacity,扩容到capacity
        if (count < capacity) {
            count = capacity;
        }
        capacity = count;
        try {
            //注册驱动
            Class.forName(dataSource.getDriver());
            for (int i = 0; i < count; i++) {
                //获取连接
                Connection connection = getConnectionByDriverManager();
                CONNECTION_LIST.add(connection);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver 不存在:" + dataSource.getDriver());
        }
    }

    /**
     * 销毁连接池
     */
    private static void destroy() {
        capacity = 5;
        if (CONNECTION_LIST.size() <= 0) {
            return;
        }
        ArrayList<Connection> removeList = new ArrayList<>();
        for (Connection connection : CONNECTION_LIST) {
            removeList.add(connection);
            try {
                //关闭连接
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        CONNECTION_LIST.removeAll(removeList);
        LOG.info("连接池已销毁");
    }


    /**
     * 扩容连接池
     */
    private static void addConnection() {
        synchronized (CONNECTION_LIST) {
            int halfCapacity = capacity / 2;
            for (int i = 0; i < halfCapacity; i++) {
                Connection connection = getConnectionByDriverManager();
                CONNECTION_LIST.add(connection);
            }
            capacity += halfCapacity;
            LOG.info("数据库连接扩容,数量增加" + halfCapacity + ",当前容量为" + capacity);
        }
    }

    /**
     * 缩减连接池连接
     */
    private void removeConnection() {
        for (int i = 0; i < REMOVE_COUNT; i++) {
            Connection connection = CONNECTION_LIST.remove(CONNECTION_LIST.size() - 1);
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warn("连接池中存在已关闭的连接");
            }
        }
        capacity -= 5;
        LOG.info("检测有数据库有部分连接长时间未使用,关闭以节省资源");
    }

    /**
     * 获取连接
     *
     * @return
     */
    public Connection getConnection() {
        synchronized (CONNECTION_LIST) {
            if (CONNECTION_LIST.size() <= 0) {
                addConnection();
            }
            Connection connection = CONNECTION_LIST.get(CONNECTION_LIST.size() - 1);
            CONNECTION_LIST.remove(connection);
            return connection;
        }
    }

    /**
     * 归还连接
     *
     * @param connection
     */
    public void putConnection(Connection connection) {

        try {
            if (connection == null || connection.isClosed()) {
                LOG.warn("归还的该数据库连接已被关闭");
                return;
            }
            synchronized (CONNECTION_LIST) {
                //如果数据库的数量大于阈值
                if (CONNECTION_LIST.size() > THRESHOLD) {
                    if (lastTime == 0L) {
                        lastTime = System.currentTimeMillis();
                    } else if (System.currentTimeMillis() - lastTime > THRESHOLD_TIME) {
                        //时间超过一分钟
                        removeConnection();
                    }
                }
                CONNECTION_LIST.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
