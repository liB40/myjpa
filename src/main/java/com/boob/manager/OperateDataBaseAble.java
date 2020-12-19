package com.boob.manager;

/**
 * 操作数据库接口
 * @author jangbao - 2020/12/18 11:26
 */
public interface OperateDataBaseAble {

    /**
     * 即时查询
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    <T> T find(Class<T> clazz, Object id);

    /**
     * 保存
     *
     * @param obj
     */
    void persist(Object obj);

    /**
     * 删除
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    <T> T removeById(Class<T> clazz, Object id);


    /**
     * 修改
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> T merge(T obj);
}
