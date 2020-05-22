package com.boob.transaction;

/**
 * 事务支持接口
 */
public interface EntityTransaction {

    /**
     * 开启事务
     */
    void begin();

    /**
     * 提交事务
     */
    void commit();

    /**
     * 回滚事务
     */
    void rollback();


    /**
     * 事务是否已激活
     *
     * @return
     */
    boolean isActive();

}
