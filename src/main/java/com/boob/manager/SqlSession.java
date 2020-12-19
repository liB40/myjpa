package com.boob.manager;

/**
 * @author jangbao - 2020/12/18 10:56
 */
public interface SqlSession extends OperateDataBaseAble {

    /**
     * 提交
     */
    void commit();

    /**
     * 回滚
     */
    void rollback();

    /**
     * 关闭
     */
    void close();
}
