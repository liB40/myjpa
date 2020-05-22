package com.boob.repository;

import java.util.List;

public interface CrudRepository<T, ID> extends Repository<T, ID> {

    /**
     * 保存
     *
     * @param t
     * @return
     */
    T save(T t);

    /**
     * 根据id 查询
     *
     * @param id
     * @return
     */
    T findById(ID id);


    /**
     * 判断是否存在
     *
     * @param id
     * @return
     */
    boolean existsById(ID id);

    /**
     * 查询所有
     *
     * @return
     */
    List<T> findAll();

    /**
     * 更新
     *
     * @param t
     * @return
     */
    T update(T t);

    /**
     * 统计数量
     *
     * @return
     */
    long count();

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteById(ID id);

}
